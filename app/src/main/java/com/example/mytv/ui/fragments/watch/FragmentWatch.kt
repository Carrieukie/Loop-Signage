package com.example.mytv.ui.fragments.watch

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import android.database.Cursor
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.preference.PreferenceManager
import com.example.mytv.R
import com.example.mytv.datastructures.ResizeModes
import com.example.mytv.model.Weather
import com.example.mytv.utils.Resource
import com.example.mytv.utils.UtilityFunctions.getOrientation
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.upstream.DataSource
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.Util
import com.vmadalin.easypermissions.EasyPermissions
import com.vmadalin.easypermissions.dialogs.SettingsDialog
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.roundToInt


@AndroidEntryPoint
class FragmentWatch : Fragment(R.layout.fragment_watch), EasyPermissions.PermissionCallbacks,
    SharedPreferences.OnSharedPreferenceChangeListener {

    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var resizeModes : ResizeModes
    private val viewModel by viewModels<FragmentWatchViewModel>()
    //views
    //PlayerView
    private var playerView: PlayerView? = null
    private var fillModesButton: ImageButton? = null
    private var player: SimpleExoPlayer? = null
    private var selectFolder: ImageButton? = null
    //Weather
    private var city : TextView? = null
    private var date : TextView? = null
    private var image : ImageView? = null
    private var temp : TextView? = null
    private var weatherDesc : TextView? = null

    //Booleans
    private var playWhenReady = true


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        playerView = view.findViewById(R.id.video_view)

        val settingsButton: ImageButton = view.findViewById(R.id.exo_settings)
        settingsButton.setOnClickListener {
            it.findNavController().navigate(R.id.Watch_to_settings)
        }

        val orientation: ImageButton = view.findViewById(R.id.orientation)
        orientation.setOnClickListener {
            switchOrientation()
        }

        fillModesButton = view.findViewById(R.id.exo_resize_button)
        fillModesButton?.setOnClickListener {
            changeFillMode()
        }

        selectFolder = view.findViewById(R.id.exo_select_folder)
        selectFolder?.setOnClickListener {
            performFileSearch()
        }

        city = view.findViewById(R.id.textView_city)
        date = view.findViewById(R.id.textView_date)
        image = view.findViewById(R.id.imegeview_weather)
        temp = view.findViewById(R.id.textView_temp)
        weatherDesc = view.findViewById(R.id.textView_desc)


        if (!hasStoragePermissions()) {
            requestStoragePermission()
        }
        setUpSharedPreferences()

    }


    private fun obsereWeatherData() {
        viewModel.weather(getLocation()).observe(viewLifecycleOwner, {
            when (it) {
                is Resource.Success -> {
                    bindWeatherData(it.data)
                }
                is Resource.Error -> {
                    bindWeatherData(it.data)
                }
                is Resource.Loading -> {
                    bindWeatherData(it.data)
                }
            }
        })
    }

    private fun bindWeatherData(data: Weather?) {
        city?.text = data?.city?.name
        date?.text = getCurrentTime()
        temp?.text = data?.list?.get(0)?.main?.feelsLike?.minus(273.15)?.roundToInt().toString().plus("\u00b0")
        weatherDesc?.text = data?.list?.get(0)?.weather?.get(0)?.description
    }

    private fun getCurrentTime(): String {
        val sdf = SimpleDateFormat("dd, MMMM yyyy HH:mm", Locale.getDefault())
        return sdf.format(Date())
    }

    private fun changeFillMode() {

        sharedPreferences.edit().apply {
            putInt("resizeMode", resizeModes.getNextMode())
            apply()
        }

    }

    private fun switchOrientation() {
        val orientation = sharedPreferences.getString("orientation", "Landscape")
        sharedPreferences.edit().apply {
            if (orientation == "Landscape") {
                putString("orientation", "Portrait")
            } else {
                putString("orientation", "Landscape")
            }
            apply()
        }

    }

    private fun initializePlayer() {

        player = SimpleExoPlayer.Builder(requireContext()).build()
        playerView!!.player = player
        player!!.addMediaSources(getMediaSources())
        player!!.playWhenReady = playWhenReady
        player!!.seekTo(viewModel.currentWindow, viewModel.playbackPosition)
        player!!.prepare()

    }


    private fun getMediaSources(): List<MediaSource> {

        return getAllMedia().map {

            buildMediaSourceNew(it!!.toUri())

        }

    }

    private fun buildMediaSourceNew(uri: Uri): MediaSource {
        val dataSourceFactroy: DataSource.Factory =
            DefaultDataSourceFactory(requireContext(), Util.getUserAgent(requireContext(), "My TV"))
        return ProgressiveMediaSource.Factory(dataSourceFactroy).createMediaSource(
            MediaItem.fromUri(
                uri
            )
        )
    }

    private fun releasePlayer() {
        if (player != null) {
            viewModel.playbackPosition = player!!.currentPosition
            viewModel.currentWindow = player!!.currentWindowIndex
            playWhenReady = player!!.playWhenReady
            player!!.release()
            player = null
        }
    }

    @SuppressLint("InlinedApi")
    private fun hideSystemUi() {
        playerView!!.systemUiVisibility = (View.SYSTEM_UI_FLAG_LOW_PROFILE
                or View.SYSTEM_UI_FLAG_FULLSCREEN
                or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION)
    }


    override fun onStart() {
        super.onStart()
        if (Util.SDK_INT > 23) {
            initializePlayer()
        }
    }

    override fun onResume() {
        super.onResume()
        hideSystemUi()
        if (Util.SDK_INT <= 23 || player == null) {
            initializePlayer()
        }
        obsereWeatherData()
    }


    override fun onPause() {
        super.onPause()
        if (Util.SDK_INT <= 23) {
            releasePlayer()
        }
    }

    override fun onStop() {
        super.onStop()
        if (Util.SDK_INT > 23) {
            releasePlayer()
        }
    }


    private fun getAllMedia(): ArrayList<String?> {

        if (!hasStoragePermissions()) {
            requestStoragePermission()
        }

        val videoItemHashSet: HashSet<String> = HashSet()

        val collection =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                MediaStore.Video.Media.getContentUri(
                    MediaStore.VOLUME_EXTERNAL
                )
            } else {
                MediaStore.Video.Media.EXTERNAL_CONTENT_URI
            }

        val projection = arrayOf(
            MediaStore.Video.VideoColumns.DATA,
            MediaStore.Video.Media.DISPLAY_NAME
        )

        val foldername = sharedPreferences.getString("folderName", "")!!

        val selection = MediaStore.Video.Media.DATA + " like?"

        val selectionArgs = arrayOf("%$foldername%")

        val cursor: Cursor? = requireContext().contentResolver.query(
            collection,
            projection,
            selection,
            selectionArgs,
            null
        )

        try {
            cursor?.moveToFirst()
            if (cursor != null) {
                do {
                    cursor.let {
                        videoItemHashSet.add(it.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATA)))
                    }

                } while (cursor.moveToNext())
            }
            cursor?.close()
        } catch (e: Exception) {
            toast(e.message!!)
            e.printStackTrace()
        } finally {
            cursor?.close()
        }

        return ArrayList(videoItemHashSet)
    }


    private fun hasStoragePermissions() = EasyPermissions.hasPermissions(
        requireContext(),
        Manifest.permission.READ_EXTERNAL_STORAGE
    )

    private fun requestStoragePermission() {
        EasyPermissions.requestPermissions(
            this,
            "This application cannot work without this permission",
            PERMISSION_LOCATION_REQUEST_CODE,
            Manifest.permission.READ_EXTERNAL_STORAGE
        )

    }


    private fun toast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)

    }

    override fun onPermissionsDenied(requestCode: Int, perms: List<String>) {

        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            SettingsDialog.Builder(requireContext()).build().show()
        } else {
            requestStoragePermission()
        }

    }


    override fun onPermissionsGranted(requestCode: Int, perms: List<String>) {
        initializePlayer()
    }



    private fun setUpSharedPreferences() {

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(requireContext())

        val resizeMode = sharedPreferences.getInt(
            "resizeMode",
            AspectRatioFrameLayout.RESIZE_MODE_FIT
        )
        resizeModes = ResizeModes(resizeMode)

        sharedPreferences.registerOnSharedPreferenceChangeListener(this)
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, key: String?) {

        if (key == "orientation") {
            val orientation = getOrientation(sharedPreferences?.getString(key, "Landscape")!!)
            requireActivity().requestedOrientation = orientation
        }

        if (key == "resizeMode"){
            val resizeMode = sharedPreferences?.getInt(
                "resizeMode",
                AspectRatioFrameLayout.RESIZE_MODE_FIT
            )
            toast(modes[resizeMode!!])
            fillModesButton!!.setImageDrawable(
                ContextCompat.getDrawable(
                    requireContext(),
                    drawables[resizeMode]
                )
            )
            playerView!!.resizeMode = resizeModes.modes[resizeMode]
        }

        if (key == "folderName"){
            initializePlayer()
        }


    }

    private fun getLocation(): String {
        return sharedPreferences?.getString("weather_location", "Nairobi")!!
    }

    override fun onDestroy() {
        super.onDestroy()
        PreferenceManager.getDefaultSharedPreferences(requireContext())
            .unregisterOnSharedPreferenceChangeListener(this)
    }

    private fun performFileSearch() {
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT_TREE)
        startActivityForResult(intent, PICK_FOLDER_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK && requestCode == PICK_FOLDER_REQUEST_CODE){
//            toast(data?.data.toString())
            val foldername = data?.data.toString()
                .split("%2F")
                .last()
                .split("%3A")
                .last()
                .replace("%20"," ")


            sharedPreferences.edit().apply{
                putString("folderName", foldername)
                apply()
            }

        }

    }

    private val modes = listOf(
        "Zoom",
        "Fill",
        "Fixed Height",
        "Fixed Width",
        "Fit"
    )

    private val drawables = listOf(
        R.drawable.ic_crop_to_fit,
        R.drawable.ic_fit_to_screen,
        R.drawable.ic_fixed_height,
        R.drawable.ic_fixed_width,
        R.drawable.ic_exit_fullscreen
    )

    companion object {
        private const val PERMISSION_LOCATION_REQUEST_CODE: Int = 0
        private const val PICK_FOLDER_REQUEST_CODE: Int = 0
        private const val TAG = "FragmentWatch"

    }

}


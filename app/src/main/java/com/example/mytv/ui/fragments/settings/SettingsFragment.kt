package com.example.mytv.ui.fragments.settings

import android.content.SharedPreferences
import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.PreferenceManager
import com.example.mytv.R
import com.example.mytv.utils.UtilityFunctions
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SettingsFragment : PreferenceFragmentCompat(),
        SharedPreferences.OnSharedPreferenceChangeListener {
        private lateinit var sharedPreferences: SharedPreferences

        override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey)
        }

        override fun onSharedPreferenceChanged(
            sharedPreferences: SharedPreferences?,
            key: String?
        ) {

            if (key == "orientation") {
                val orientation =
                    UtilityFunctions.getOrientation(
                        sharedPreferences?.getString(
                            key,
                            "Landscape"
                        )!!
                    )
                requireActivity().requestedOrientation = orientation

            }
        }

        override fun onDestroy() {
            super.onDestroy()
            sharedPreferences = PreferenceManager.getDefaultSharedPreferences(requireContext()).also {
                it.registerOnSharedPreferenceChangeListener(this)
            }

            sharedPreferences.unregisterOnSharedPreferenceChangeListener(this)
        }
    }


package com.example.mytv.datastructures

import com.google.android.exoplayer2.ui.AspectRatioFrameLayout


class ResizeModes(currentMode: Int?) {


    private var current = 0
    val modes = listOf(
        AspectRatioFrameLayout.RESIZE_MODE_ZOOM,
        AspectRatioFrameLayout.RESIZE_MODE_FILL,
        AspectRatioFrameLayout.RESIZE_MODE_FIXED_HEIGHT,
        AspectRatioFrameLayout.RESIZE_MODE_FIXED_WIDTH,
        AspectRatioFrameLayout.RESIZE_MODE_FIT
    )


    init {

        for (mode in modes) {
            if (currentMode != mode) {
                ++current
                break
            }
        }


    }


    fun getNextMode(): Int {
        current++
        if (current >= modes.size) {
            current = 0
        }
        return modes[current]
    }
}
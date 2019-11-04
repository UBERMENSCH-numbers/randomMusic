package com.example.randommusic.interfaces

import com.example.randommusic.ProgressBarType

interface ISeekBarController {
    fun setMax(type : ProgressBarType, max : Int)
    fun setProgress (type: ProgressBarType, progress : Int)

}


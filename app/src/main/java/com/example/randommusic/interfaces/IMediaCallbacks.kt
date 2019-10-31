package com.example.randommusic.interfaces


interface IMediaCallbacks {
    fun mediaBuffering (progress: Int, duration: Int = -1)
    fun updateSeekbar (second : Int)
}

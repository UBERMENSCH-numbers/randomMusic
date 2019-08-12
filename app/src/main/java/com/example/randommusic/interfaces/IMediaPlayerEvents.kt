package com.example.randommusic.interfaces

import com.example.randommusic.MediaCallbacks

interface IMediaPlayerEvents {
    fun playMedia ()
    fun pauseMedia ()
    fun stopMedia ()
    fun seekMediaTo (position : Int)
    fun getMediaDuration () : Int
    fun setMediaPath (path: String)
    fun initMediaPlayer ()
    fun onDestroy ()
    fun setMediaCallbacks (callbacks: MediaCallbacks)
}


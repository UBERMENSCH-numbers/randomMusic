package com.example.randommusic

import android.util.Log
import com.example.randommusic.interfaces.IMediaPlayerEvents
import com.example.randommusic.interfaces.IMediaCallbacks

class MediaPlayerControllerConnection (val IMediaPlayerEvents: IMediaPlayerEvents){
    val LOG = "MediaPlayerConnection"
    var isClientConnected = false
    var mediaCallbacks : IMediaCallbacks? = null


    fun connectToMediaPlayer (mediaCallbacks: IMediaCallbacks): IMediaPlayerEvents {
        this.mediaCallbacks = mediaCallbacks
        isClientConnected = true
        IMediaPlayerEvents.setMediaCallbacks(mediaCallbacks)
        Log.v(LOG, "connecting to mediaPlayer")
        return IMediaPlayerEvents
    }

}
package com.example.randommusic

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import com.example.randommusic.interfaces.IMediaPlayerEvents


class MediaPlayerService : Service() {
    private val mediaPlayerController = MediaPlayerController() as IMediaPlayerEvents
    private val binderIns = BinderClass()

    inner class BinderClass : Binder () {
        fun getMediaPlayerController() = this@MediaPlayerService.mediaPlayerController
        fun setMediaCallbacks(callbacks: MediaCallbacks){
            mediaPlayerController.setMediaCallbacks(callbacks)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayerController.onDestroy()
    }

    override fun onBind(p0: Intent?): IBinder? {
        mediaPlayerController.initMediaPlayer()
        mediaPlayerController.setMediaPath(p0!!.getStringExtra("path"))
        return binderIns
    }




}
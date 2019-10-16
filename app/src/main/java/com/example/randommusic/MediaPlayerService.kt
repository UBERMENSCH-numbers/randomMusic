package com.example.randommusic

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import com.example.randommusic.interfaces.IMediaPlayerEvents


class MediaPlayerService : Service() {
    private val mediaPlayerControllerConnection = MediaPlayerController().mediaPlayerConnection
    private val binderIns = BinderClass()

    inner class BinderClass : Binder () {
        fun getMediaPlayerControllerConnection() = mediaPlayerControllerConnection
    }

    override fun onDestroy() {
        isRunning = false
        super.onDestroy()
        mediaPlayerControllerConnection.iMediaPlayerEvents.onDestroy()
    }

    override fun onBind(p0: Intent?): IBinder? {
        mediaPlayerControllerConnection.iMediaPlayerEvents.initMediaPlayer()
        mediaPlayerControllerConnection.iMediaPlayerEvents.setMediaPath(p0!!.getStringExtra("path"))
        return binderIns
    }

    override fun onCreate() {
        super.onCreate()
        isRunning = true
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        isRunning = true
        return super.onStartCommand(intent, flags, startId)
    }

    companion object {
        var isRunning = false
    }






}
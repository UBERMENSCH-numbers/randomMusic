package com.example.randommusic

import android.media.AudioAttributes
import android.media.AudioManager
import android.media.MediaPlayer
import android.media.MediaPlayer.*
import android.util.Log
import com.example.randommusic.interfaces.IMediaPlayerEvents

class MediaPlayerController : OnCompletionListener, OnPreparedListener,
OnErrorListener, OnSeekCompleteListener, OnInfoListener, OnBufferingUpdateListener, IMediaPlayerEvents {
    private var mediaCallbacks : MediaCallbacks? = null

    private var resumePosition: Int = 0
    private lateinit var mediaPlayer: MediaPlayer
    val TAG = "Player Event"

    override fun setMediaCallbacks(callbacks: MediaCallbacks) {
        mediaCallbacks = callbacks
    }

    override fun onCompletion(p0: MediaPlayer?) {
        Log.v(TAG, "OnCompletion")
    }

    override fun onPrepared(mp: MediaPlayer?) {
        mp?.start() ?: Log.e(TAG, "Null Player in OnPrepared")
    }

    override fun onError(p0: MediaPlayer?, err: Int, extra: Int): Boolean {
        when (err) {
            MEDIA_ERROR_NOT_VALID_FOR_PROGRESSIVE_PLAYBACK -> Log.d("MPController Error",
                "MEDIA ERROR NOT VALID FOR PROGRESSIVE PLAYBACK $extra")
            MEDIA_ERROR_SERVER_DIED -> Log.d("MPController Error", "MEDIA ERROR SERVER DIED $extra")
            MEDIA_ERROR_UNKNOWN -> Log.d("MPController Error", "MEDIA ERROR UNKNOWN $extra")
        }
        return false
    }

    override fun onSeekComplete(p0: MediaPlayer?) {
        Log.v(TAG, "OnSeekComplete ${p0?.currentPosition?.div(1000) ?: "NULLMEDIAPLAYER"}")
    }

    override fun onInfo(mp: MediaPlayer?, p1: Int, p2: Int): Boolean {
        Log.v(TAG, "OnInfo $p1 $p2")
        return true
    }

    override fun onBufferingUpdate(mp: MediaPlayer?, progress: Int) {
        mediaCallbacks?.mediaBuffering(progress, mediaPlayer.duration) ?: Log.e(TAG, "MediaBuffedCallback not set")
        if (progress == 100) startUpdatingSeekbar()
        Log.v(TAG, "Buffering $progress%")
    }

    override fun setMediaPath (path: String) {
        mediaPlayer.reset()
        mediaPlayer.setDataSource(path)
        mediaPlayer.prepareAsync()
    }

    override fun initMediaPlayer() {
        mediaPlayer = MediaPlayer()
        mediaPlayer.apply {
            setOnCompletionListener(this@MediaPlayerController)
            setOnErrorListener(this@MediaPlayerController)
            setOnPreparedListener(this@MediaPlayerController)
            setOnBufferingUpdateListener(this@MediaPlayerController)
            setOnSeekCompleteListener(this@MediaPlayerController)
            setOnInfoListener(this@MediaPlayerController)
            setAudioAttributes(AudioAttributes.Builder()
                .setLegacyStreamType(AudioManager.STREAM_MUSIC)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .build())
        }
    }

    override fun onDestroy() {
        mediaPlayer.stop()
        mediaPlayer.release()
    }

    override fun playMedia() {
        if (!mediaPlayer.isPlaying) {
            mediaPlayer.start()
        }
    }

    override fun stopMedia() {
        if (mediaPlayer.isPlaying) {
            mediaPlayer.stop()
        }
    }

    override fun pauseMedia() {
        if (mediaPlayer.isPlaying) {
            mediaPlayer.pause()
            resumePosition = mediaPlayer.currentPosition
        }
    }

    override fun seekMediaTo(position: Int) {
        if (position >= 0) {
            mediaPlayer.seekTo(position)
        }
    }

    override fun getMediaDuration () = mediaPlayer.duration

    private fun startUpdatingSeekbar() {
        val thread = Thread(Runnable {
            while (true) {
                mediaCallbacks?.updateSeekbar(mediaPlayer.currentPosition)
                Thread.sleep(1000)
            }
        })
        thread.start()
    }

}

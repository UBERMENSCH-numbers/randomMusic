package com.example.randommusic

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.*
import android.util.Log
import android.view.View
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity
import com.example.randommusic.interfaces.IMediaPlayerEvents
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity(), MediaCallbacks {
    private lateinit var mMediaController: IMediaPlayerEvents

    private lateinit var seek: SeekBar
    private var isBound = false
    private var isBuffered = false

    override fun updateSeekbar(second: Int) {
        seek.progress = second
    }

    override fun mediaBuffering(progress: Int,duration: Int) {
        seek.secondaryProgress = progress
        if (progress == 100) {
            isBuffered = true
            seek.max = duration
            seek.secondaryProgress = seek.max
            Log.v("Player Event", "Media buffered, duration is ${duration/60000} min, ${duration/1000 - duration/60000*60} sec")
        }

    }

    private val mConnection = object : ServiceConnection {

        override fun onServiceConnected(className: ComponentName, service: IBinder) {
            val binder = service as MediaPlayerService.BinderClass
            mMediaController = binder.getMediaPlayerControllerConnection().connectToMediaPlayer(this@MainActivity)
            mMediaController.setMediaCallbacks(this@MainActivity)
            seek.progress = 0
            seek.secondaryProgress = 0
            isBound = true
        }

        override fun onServiceDisconnected(arg0: ComponentName) {
            isBound = false
        }
    }

    override fun onCreate(savedInstanceState:Bundle?)  {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var intent = Intent(this, MediaPlayerService::class.java)
        intent.putExtra("path","https://firebasestorage.googleapis.com/v0/b/randommusic-40106.appspot.com/o/music%2FBrain%20Damage." +
                "mp3?alt=media&token=cafd184b-7152-4025-b7b2-d4bd363cbd42")
        bindService(intent, mConnection, Context.BIND_AUTO_CREATE)
        seek = findViewById(R.id.seekBar)
        seek.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onStartTrackingTouch(mp: SeekBar?) {
            }
            override fun onProgressChanged(mp: SeekBar?, p1: Int, p2: Boolean) {
                if(!isBuffered) {
                    mp?.progress = 0
                    val v = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        v.vibrate(VibrationEffect.createOneShot(50, VibrationEffect.DEFAULT_AMPLITUDE))
                    } else {
                        v.vibrate(50)
                    }
                }

            }
            override fun onStopTrackingTouch(p0: SeekBar?) {
                if (isBuffered) {
                    Log.v("Player Event", "Seekbar.max = ${mMediaController.getMediaDuration()}")
                    mMediaController.seekMediaTo(p0!!.progress)
                } else Snackbar.make(findViewById(R.id.mainActivityConstraint),
                    "You can`t use snackbar while media have not buffered", Snackbar.LENGTH_SHORT).show()
            }
        })
    
//        if (MediaPlayerService.isRunning) {
//
//            Log.v("MainActivity", "mediaCallbackSet")
//        }

    }

    fun onClick (view: View) {
        when(view.id) {
            R.id.resume -> mMediaController.playMedia()
            //R.id.next ->
            R.id.pause -> mMediaController.pauseMedia()
        }
    }
}



interface MediaCallbacks {
    fun mediaBuffering (progress: Int, duration: Int = -1)
    fun updateSeekbar (second : Int)
}

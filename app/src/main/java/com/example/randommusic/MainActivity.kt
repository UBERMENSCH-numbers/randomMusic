package com.example.randommusic

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.*
import android.util.Log
import android.view.View
import android.widget.ImageButton
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.example.randommusic.interfaces.IMediaCallbacks
import com.example.randommusic.interfaces.IMediaPlayerEvents
import com.example.randommusic.interfaces.ISeekBarController
import com.example.randommusic.mvp.contracts.MainActivityContract

class MainActivity : AppCompatActivity(), MainActivityContract.View {
    val TAG = "MainActivityTag"
    lateinit var model : ViewModel
    lateinit var presenter: MainActivityContract.Presenter
    lateinit var seekBar : SeekBar
    lateinit var player : IMediaPlayerEvents

    override fun createConnection(mediaCallbacks: IMediaCallbacks) {
            val connection = object : ServiceConnection {
                override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
                    val binder = service as MediaPlayerService.BinderClass
                    model.connection = binder.getMediaPlayerControllerConnection()
                    player = model.updateConnection(mediaCallbacks)
                    presenter.onReady(player)
                }

                override fun onServiceDisconnected(name: ComponentName?) {
                    Log.e(TAG, "Service is Dead")
                }
            }
            val myService = startService(Intent(this, MediaPlayerService::class.java))
            bindService(Intent(this, MediaPlayerService::class.java), connection, Context.BIND_AUTO_CREATE)
    }

    override fun restoreConnection(mediaCallbacks: IMediaCallbacks) {
        player = model.updateConnection(mediaCallbacks)
        Log.v(TAG, "connection restored")
        presenter.onReady(player)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        model = ViewModelProviders.of(this).get(ViewModel::class.java)
        setContentView(R.layout.activity_main)
        presenter = model.presenter
        presenter.attachView(this)
        prepareView()
    }

    override fun seekBarTo(type: String, position: Int) {
        if (seekBar.max != player.getMediaDuration()) seekBar.max = player.getMediaDuration()
        if (type == "primary") seekBar.progress = position
        else seekBar.secondaryProgress = position

    }

    private fun prepareView() {
        seekBar = findViewById(R.id.seekBar)
        val listener = View.OnClickListener {



            when (it.id) {
                R.id.next -> presenter.onAction(Action.NEXT)
                R.id.pause -> presenter.onAction(Action.PAUSE)
                R.id.resume -> presenter.onAction(Action.START)
            }
        }
        findViewById<ImageButton>(R.id.next).setOnClickListener(listener)
        findViewById<ImageButton>(R.id.pause).setOnClickListener(listener)
        findViewById<ImageButton>(R.id.resume).setOnClickListener(listener)
        presenter.viewIsReady()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.detachView()
    }
}




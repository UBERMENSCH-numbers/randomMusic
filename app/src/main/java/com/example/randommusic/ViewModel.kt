package com.example.randommusic

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.randommusic.interfaces.IMediaCallbacks
import com.example.randommusic.interfaces.IMediaPlayerEvents
import com.example.randommusic.mvp.presenter.MainActivityPresenter

class ViewModel() : ViewModel() {
    val TAG = "viewModelTag"
    val presenter = MainActivityPresenter()
    lateinit var connection : MediaPlayerControllerConnection

    fun updateConnection(callbacks: IMediaCallbacks) : IMediaPlayerEvents {
        return connection.connectToMediaPlayer(callbacks)
    }

}
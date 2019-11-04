package com.example.randommusic.mvp.presenter

import android.util.Log
import com.example.randommusic.Action
import com.example.randommusic.MediaPlayerService
import com.example.randommusic.interfaces.IMediaPlayerEvents
import com.example.randommusic.mvp.contracts.MainActivityContract

class MainActivityPresenter() : MainActivityContract.Presenter{
    val TAG = "MainPresenterTag"
    var view : MainActivityContract.View? = null
    var i = 0;
    override fun attachView(mvpView: MainActivityContract.View) {
        Log.v(TAG, "View attached to MainPresenter")
        this.view = mvpView

        if (!MediaPlayerService.isRunning) {
            view!!.createConnection(this)
            MediaPlayerService.isRunning = true
        } else {
            view!!.restoreConnection(this)
        }

    }

    override fun viewIsReady() {

    }

    override fun detachView() {
        Log.v(TAG, "View detached from MainPresenter")
        view = null
    }

    override fun destroy() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun mediaBuffering(progress: Int, duration: Int) {
        Log.v(TAG, "mediaBuffering")
    }

    override fun updateSeekbar(second: Int) {
        Log.v(TAG, "updateSeekbar")
    }

    override fun onAction(action : Action) {
        when (action) {
            Action.START -> Log.e(TAG, "implement me")
            Action.PAUSE -> Log.e(TAG, "implement me")
            Action.NEXT -> Log.e(TAG, "implement me")
        }
    }

    override fun onReady(player : IMediaPlayerEvents) {
        player.setMediaPath("")
        player.playMedia()
    }
}


package com.example.randommusic.mvp.presenter

import android.util.Log
import com.example.randommusic.Action
import com.example.randommusic.mvp.contracts.MainActivityContract

class MainActivityPresenter() : MainActivityContract.Presenter{
    val TAG = "MainPresenterTag"
    var view : MainActivityContract.View? = null
    var isFirstAttaching = true

    override fun attachView(mvpView: MainActivityContract.View) {
        Log.v(TAG, "View attached to MainPresenter")
        this.view = mvpView

        if (isFirstAttaching) {
            isFirstAttaching = false
            view!!.createConnection(this)
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

}


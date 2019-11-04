package com.example.randommusic.mvp.contracts

import com.example.randommusic.Action
import com.example.randommusic.interfaces.IPresenter
import com.example.randommusic.interfaces.IView
import com.example.randommusic.interfaces.IMediaCallbacks
import com.example.randommusic.interfaces.IMediaPlayerEvents

class MainActivityContract {
    interface Presenter : IPresenter<View> , IMediaCallbacks {
        fun onAction(action : Action)
        fun onReady(player : IMediaPlayerEvents)
    }

    interface View : IView {
        fun restoreConnection (mediaCallbacks : IMediaCallbacks)
        fun createConnection (mediaCallbacks: IMediaCallbacks)
        fun seekBarTo(position : Int, type : String? = "primary")
    }

    interface Model {

    }
}
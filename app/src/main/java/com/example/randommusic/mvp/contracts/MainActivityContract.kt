package com.example.randommusic.mvp.contracts

import com.example.randommusic.Action
import com.example.randommusic.interfaces.*

class MainActivityContract {
    interface Presenter : IPresenter<View> , IMediaCallbacks {
        fun onAction(action : Action)
        fun onReady(player : IMediaPlayerEvents)
    }

    interface View : IView{
        fun restoreConnection (mediaCallbacks : IMediaCallbacks)
        fun createConnection (mediaCallbacks: IMediaCallbacks)
        fun seekBarTo(type : String, position : Int)
    }

    interface Model {

    }
}
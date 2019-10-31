package com.example.randommusic.mvp.contracts

import com.example.randommusic.Action
import com.example.randommusic.interfaces.IPresenter
import com.example.randommusic.interfaces.IView
import com.example.randommusic.interfaces.IMediaCallbacks

class MainActivityContract {
    interface Presenter : IPresenter<View> , IMediaCallbacks {
        fun onAction(action : Action)
    }

    interface View : IView {
        fun restoreConnection (mediaCallbacks : IMediaCallbacks)
        fun createConnection (mediaCallbacks: IMediaCallbacks)

    }

    interface Model {

    }
}
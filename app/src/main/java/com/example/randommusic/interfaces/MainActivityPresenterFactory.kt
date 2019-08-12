package com.example.randommusic.interfaces

import android.graphics.ColorSpace.Model
import com.example.randommusic.mvp.contracts.MainActivityContract
import com.example.randommusic.mvp.presenter.MainActivityPresenter


class MainActivityPresenterFactory<T>(internal var model: MainActivityContract.Model) : IPresenterFactory<T> {

    override fun create(): T {
        return MainActivityPresenter(model) as T
    }
}
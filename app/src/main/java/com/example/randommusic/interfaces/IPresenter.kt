package com.example.randommusic.interfaces

interface IPresenter<V : IView> {

    fun attachView(mvpView: V)

    fun viewIsReady()

    fun detachView()

    fun destroy()
}

package com.example.randommusic.interfaces

interface IPresenterFactory<T> {
    fun create(): T
}

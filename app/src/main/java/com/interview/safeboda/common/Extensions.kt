package com.interview.safeboda.common

import android.view.View
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

fun Disposable.disposedBy(bag: CompositeDisposable) {
    bag.add(this)
}
typealias VoidLambda = () -> Unit
typealias StringLambda = (String) -> Unit

typealias ItemClickedlambda = (v: View, position: Int) -> Unit

sealed class Result<out T> {
    data class Success<out T>(val data: T) : Result<T>()
    data class Error<out T>(val throwable: Throwable) : Result<T>()
}


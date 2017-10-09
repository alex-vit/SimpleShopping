package com.alexvit.simpleshopping.base

import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

/**
 * Created by Aleksandrs Vitjukovs on 10/8/2017.
 */

abstract class BaseViewModel {

    protected val compositeSub = CompositeDisposable()

    protected fun <T> subscribe(
            observable: Observable<T>,
            onNext: (T) -> Unit = {},
            onError: (Throwable) -> Unit = {},
            onComplete: () -> Unit = {},
            subscribeOn: Scheduler = Schedulers.io(),
            observeOn: Scheduler = AndroidSchedulers.mainThread()
    ) {
        val sub = observable
                .subscribeOn(subscribeOn)
                .observeOn(observeOn)
                .subscribe(onNext, onError, onComplete)
        compositeSub.add(sub)
    }

    open fun onDestroy() {
        compositeSub.clear()
    }

}
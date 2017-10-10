package com.alexvit.simpleshopping.features.list

import android.util.Log
import com.alexvit.simpleshopping.base.BaseViewModel
import com.alexvit.simpleshopping.data.models.Item
import com.alexvit.simpleshopping.data.source.ListsRepository
import io.reactivex.BackpressureStrategy.BUFFER
import io.reactivex.BackpressureStrategy.LATEST
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject

/**
 * Created by Aleksandrs Vitjukovs on 10/8/2017.
 */

class ListViewModel(private val repository: ListsRepository) : BaseViewModel() {

    private val itemsSubject = BehaviorSubject.create<List<Item>>()
    private val itemUpdatesSubject = PublishSubject.create<Pair<Item, Item>>()
    private val showSignIntoDropboxSubject = BehaviorSubject.createDefault(false)

    init {
        subscribe(repository.getAllItems(), itemsSubject::onNext)

        if (repository.getDropBoxToken() == null) {
            showSignIntoDropboxSubject.onNext(true)
        } else {
            downloadDb()
        }
    }

    fun items() = itemsSubject
            .toFlowable(LATEST)
            .toObservable()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

    fun itemUpdates() = itemUpdatesSubject
            .toFlowable(BUFFER)
            .toObservable()

    fun showSignIntoDropbox() = showSignIntoDropboxSubject
            .toFlowable(LATEST)
            .toObservable()

    fun check(item: Item, checked: Boolean) {
        val newItem = item.copy(checked = checked)
        itemUpdatesSubject.onNext(Pair(item, newItem))
        subscribe(repository.insert(newItem))
    }

    fun delete(item: Item) {
        subscribe(repository.delete(item))
    }

    fun saveDropboxToken(token: String) {
        repository.setDropBoxToken(token)
        showSignIntoDropboxSubject.onNext(false)
    }

    private fun downloadDb() {

        val o = repository.isRemoteDbNewer()
                .flatMap { newer ->
                    if (newer) {
                        Log.d("VM", "remote db newer, downloading")
                        repository.downloadDb()
                    } else {
                        Log.d("VM", "remote db older, skipping")
                        Observable.empty()
                    }
                }.doOnComplete(this::reload)

        subscribe(o)
    }

    private fun reload() {
        compositeSub.clear()
        subscribe(repository.getAllItems(), itemsSubject::onNext)
    }
}
package com.alexvit.simpleshopping.features.list

import com.alexvit.simpleshopping.base.BaseViewModel
import com.alexvit.simpleshopping.data.models.Item
import com.alexvit.simpleshopping.data.source.ListsRepository
import io.reactivex.BackpressureStrategy.BUFFER
import io.reactivex.BackpressureStrategy.LATEST
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject

/**
 * Created by Aleksandrs Vitjukovs on 10/8/2017.
 */

class ListViewModel(private val listsRepository: ListsRepository) : BaseViewModel() {

    private val itemsSubject = BehaviorSubject.create<List<Item>>()
    private val itemUpdatesSubject = PublishSubject.create<Pair<Item, Item>>()

    init {
        subscribe(listsRepository.getAllItems(), itemsSubject::onNext)
    }

    fun items() = itemsSubject
            .toFlowable(LATEST)
            .toObservable()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

    fun itemUpdates() = itemUpdatesSubject
            .toFlowable(BUFFER)
            .toObservable()

    fun check(item: Item, checked: Boolean) {
        val newItem = item.copy(checked = checked)
        itemUpdatesSubject.onNext(Pair(item, newItem))
        subscribe(listsRepository.insert(newItem))
    }

    fun delete(item: Item) {
        subscribe(listsRepository.delete(item))
    }
}
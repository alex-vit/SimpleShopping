package com.alexvit.simpleshopping.features.addedit

import com.alexvit.simpleshopping.base.BaseViewModel
import com.alexvit.simpleshopping.data.models.Item
import com.alexvit.simpleshopping.data.source.ListsRepository
import io.reactivex.BackpressureStrategy
import io.reactivex.subjects.BehaviorSubject

/**
 * Created by Aleksandrs Vitjukovs on 10/8/2017.
 */

class AddEditViewModel(private val listsRepository: ListsRepository) : BaseViewModel() {

    private val savedSubject = BehaviorSubject.create<Boolean>()

    fun insert(item: Item) {
        subscribe(listsRepository.insert(item), { savedSubject.onNext(true) })
    }

    fun saved() = savedSubject.toFlowable(BackpressureStrategy.LATEST).toObservable()

}
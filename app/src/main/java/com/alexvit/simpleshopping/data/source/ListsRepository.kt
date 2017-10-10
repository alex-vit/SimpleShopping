package com.alexvit.simpleshopping.data.source

import com.alexvit.simpleshopping.data.models.Item
import com.alexvit.simpleshopping.data.source.dropbox.DropboxDatasource
import com.alexvit.simpleshopping.data.source.sql.ListsSqlDatasource
import io.reactivex.Observable

/**
 * Created by Aleksandrs Vitjukovs on 10/8/2017.
 */

class ListsRepository(private val sqlDatasource: ListsSqlDatasource,
                      private val dropboxDatasource: DropboxDatasource) {

    fun getAllItems() = sqlDatasource.getAllItems().toObservable()

    fun insert(item: Item) = Observable.fromCallable {
        sqlDatasource.insert(item)
    }

    fun delete(item: Item) = Observable.fromCallable {
        sqlDatasource.delete(item)
    }

    fun getDropBoxToken(): String? = dropboxDatasource.getToken()

}
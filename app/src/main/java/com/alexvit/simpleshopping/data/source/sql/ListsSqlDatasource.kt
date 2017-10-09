package com.alexvit.simpleshopping.data.source.sql

import com.alexvit.simpleshopping.data.models.Item

/**
 * Created by Aleksandrs Vitjukovs on 10/8/2017.
 */

class ListsSqlDatasource(private val db: ListsDatabase) {

    fun getAllItems() = db.listsDao().getAllItems()

    fun insert(item: Item) = db.listsDao().insert(item)

    fun delete(item: Item) = db.listsDao().delete(item)

}
package com.alexvit.simpleshopping.data.source.sql

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.alexvit.simpleshopping.data.models.Item
import com.alexvit.simpleshopping.data.source.sql.dao.ListsDao

/**
 * Created by Aleksandrs Vitjukovs on 10/8/2017.
 */

@Database(entities = arrayOf(Item::class), version = 1, exportSchema = false)
abstract class ListsDatabase : RoomDatabase() {
    abstract fun listsDao(): ListsDao
}
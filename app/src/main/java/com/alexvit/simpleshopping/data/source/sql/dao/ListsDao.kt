package com.alexvit.simpleshopping.data.source.sql.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy.REPLACE
import android.arch.persistence.room.Query
import com.alexvit.simpleshopping.data.models.Item
import io.reactivex.Flowable

/**
 * Created by Aleksandrs Vitjukovs on 10/8/2017.
 */

@Dao
interface ListsDao {

    @Query("SELECT * FROM items")
    fun getAllItems(): Flowable<List<Item>>

    @Insert(onConflict = REPLACE)
    fun insert(item: Item): Long

    @Delete
    fun delete(item: Item): Int

}
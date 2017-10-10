package com.alexvit.simpleshopping.data.models

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

/**
 * Created by Aleksandrs Vitjukovs on 10/8/2017.
 */

@Entity(tableName = "items")
data class Item(

        @PrimaryKey(autoGenerate = true)
        var id: Long? = null,
        var title: String? = null,
        var checked: Boolean = false

)

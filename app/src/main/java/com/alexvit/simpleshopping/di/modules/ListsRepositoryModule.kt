package com.alexvit.simpleshopping.di.modules

import android.arch.persistence.room.Room
import android.content.Context
import com.alexvit.simpleshopping.data.source.ListsRepository
import com.alexvit.simpleshopping.data.source.sql.ListsDatabase
import com.alexvit.simpleshopping.data.source.sql.ListsSqlDatasource
import com.alexvit.simpleshopping.di.qualifiers.ApplicationContext
import com.alexvit.simpleshopping.di.scopes.ApplicationScope
import dagger.Module
import dagger.Provides

/**
 * Created by Aleksandrs Vitjukovs on 10/8/2017.
 */

@Module
class ListsRepositoryModule {

    @Provides
    @ApplicationScope
    fun listsRepository(sqlDatasource: ListsSqlDatasource) = ListsRepository(sqlDatasource)

    @Provides
    @ApplicationScope
    fun listsSqlDatasource(database: ListsDatabase) = ListsSqlDatasource(database)

    @Provides
    @ApplicationScope
    fun listsDatabase(@ApplicationContext context: Context): ListsDatabase =
            Room.databaseBuilder(context, ListsDatabase::class.java, "lists.db")
                    .build()

}
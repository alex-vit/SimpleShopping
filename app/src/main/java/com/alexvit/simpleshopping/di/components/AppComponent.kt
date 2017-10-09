package com.alexvit.simpleshopping.di.components

import android.content.Context
import com.alexvit.simpleshopping.data.source.ListsRepository
import com.alexvit.simpleshopping.di.modules.AppModule
import com.alexvit.simpleshopping.di.modules.ListsRepositoryModule
import com.alexvit.simpleshopping.di.qualifiers.ApplicationContext
import com.alexvit.simpleshopping.di.scopes.ApplicationScope
import dagger.Component

/**
 * Created by Aleksandrs Vitjukovs on 10/8/2017.
 */

@ApplicationScope
@Component(modules = arrayOf(AppModule::class, ListsRepositoryModule::class))
interface AppComponent {

    @ApplicationContext
    fun context(): Context

    fun listsRepository(): ListsRepository

}

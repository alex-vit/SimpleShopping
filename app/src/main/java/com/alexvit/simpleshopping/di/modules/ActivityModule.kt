package com.alexvit.simpleshopping.di.modules

import android.content.SharedPreferences
import com.alexvit.simpleshopping.data.source.ListsRepository
import com.alexvit.simpleshopping.di.scopes.ActivityScope
import com.alexvit.simpleshopping.features.addedit.AddEditViewModel
import com.alexvit.simpleshopping.features.list.ListViewModel
import dagger.Module
import dagger.Provides

/**
 * Created by Aleksandrs Vitjukovs on 10/8/2017.
 */

@Module
class ActivityModule {

    @Provides
    @ActivityScope
    fun listViewModel(listsRepository: ListsRepository, prefs: SharedPreferences) =
            ListViewModel(listsRepository, prefs)

    @Provides
    @ActivityScope
    fun addEditViewModel(listsRepository: ListsRepository) = AddEditViewModel(listsRepository)

}
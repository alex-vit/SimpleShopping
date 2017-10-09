package com.alexvit.simpleshopping.di.components

import com.alexvit.simpleshopping.di.modules.ActivityModule
import com.alexvit.simpleshopping.di.scopes.ActivityScope
import com.alexvit.simpleshopping.features.addedit.AddEditViewModel
import com.alexvit.simpleshopping.features.list.ListViewModel
import dagger.Component

/**
 * Created by Aleksandrs Vitjukovs on 10/8/2017.
 */

@ActivityScope
@Component(dependencies = arrayOf(AppComponent::class), modules = arrayOf(ActivityModule::class))
interface ActivityComponent {

    fun listViewModel(): ListViewModel
    fun addEditViewModel(): AddEditViewModel

}

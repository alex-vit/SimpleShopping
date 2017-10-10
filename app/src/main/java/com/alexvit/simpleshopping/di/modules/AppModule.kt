package com.alexvit.simpleshopping.di.modules

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.alexvit.simpleshopping.di.qualifiers.ApplicationContext
import com.alexvit.simpleshopping.di.scopes.ApplicationScope
import dagger.Module
import dagger.Provides

/**
 * Created by Aleksandrs Vitjukovs on 10/8/2017.
 */

@Module
class AppModule(val context: Context) {

    @Provides
    @ApplicationScope
    @ApplicationContext
    fun context() = context

    @Provides
    @ApplicationScope
    fun preferences(@ApplicationContext context: Context): SharedPreferences =
            PreferenceManager.getDefaultSharedPreferences(context)

}


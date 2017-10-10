package com.alexvit.simpleshopping.di.modules

import android.content.SharedPreferences
import com.alexvit.simpleshopping.data.source.dropbox.DropboxDatasource
import com.alexvit.simpleshopping.di.scopes.ApplicationScope
import dagger.Module
import dagger.Provides

/**
 * Created by alexander.vitjukov on 10.10.2017.
 */

@Module
class DropboxModule {

    @Provides
    @ApplicationScope
    fun dropboxDatasource(prefs: SharedPreferences) = DropboxDatasource(prefs)

}
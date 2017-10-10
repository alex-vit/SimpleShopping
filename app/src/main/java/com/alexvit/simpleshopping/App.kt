package com.alexvit.simpleshopping

import android.app.Application
import android.content.Context
import com.alexvit.simpleshopping.data.source.dropbox.UploadService
import com.alexvit.simpleshopping.di.components.DaggerAppComponent
import com.alexvit.simpleshopping.di.modules.AppModule

/**
 * Created by Aleksandrs Vitjukovs on 10/8/2017.
 */

class App : Application() {

    companion object {
        fun get(context: Context) = context.applicationContext as App
    }

    val appComponent = DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .build()

    fun startDbUploadJob() = UploadService.schedule(this)
}

fun Context.appComponent() = App.get(this).appComponent
fun Context.startDbUploadJob() = App.get(this).startDbUploadJob()
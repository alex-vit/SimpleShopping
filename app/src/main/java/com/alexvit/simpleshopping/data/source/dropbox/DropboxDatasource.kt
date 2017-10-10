package com.alexvit.simpleshopping.data.source.dropbox

import android.content.SharedPreferences

/**
 * Created by alexander.vitjukov on 10.10.2017.
 */

class DropboxDatasource (private val prefs: SharedPreferences) {

    companion object {
        const val PREF_DROPBOX_TOKEN = "PREF_DROPBOX_TOKEN"
    }

    fun getToken() = prefs.getString(PREF_DROPBOX_TOKEN, null)

    fun setToken(token: String?) = prefs.edit()
            .putString(PREF_DROPBOX_TOKEN, token)
            .apply()
}
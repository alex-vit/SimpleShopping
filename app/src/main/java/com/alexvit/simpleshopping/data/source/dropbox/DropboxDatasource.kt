package com.alexvit.simpleshopping.data.source.dropbox

import android.content.SharedPreferences
import android.util.Log
import com.dropbox.core.DbxRequestConfig
import com.dropbox.core.http.OkHttp3Requestor
import com.dropbox.core.v2.DbxClientV2
import com.dropbox.core.v2.files.FileMetadata
import com.dropbox.core.v2.files.WriteMode
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.util.*

/**
 * Created by alexander.vitjukov on 10.10.2017.
 */

class DropboxDatasource(private val prefs: SharedPreferences) {

    companion object {
        const val PREF_DROPBOX_TOKEN = "PREF_DROPBOX_TOKEN"
    }

    private val config by lazy {
        DbxRequestConfig.newBuilder("Simple Shopping")
                .withHttpRequestor(OkHttp3Requestor(OkHttp3Requestor.defaultOkHttpClient()))
                .build()
    }

    fun getToken() = prefs.getString(PREF_DROPBOX_TOKEN, null)

    fun setToken(token: String?) = prefs.edit()
            .putString(PREF_DROPBOX_TOKEN, token)
            .apply()

    fun isRemoteDbNewer(token: String, filename: String, modified: Date): Boolean {
        try {
            val meta = client(token).files().getMetadata("/$filename")
            return when (meta) {
                is FileMetadata -> {
                    Log.d("DropboxDS", "remote modified = ${meta.serverModified}")
                    modified.before(meta.serverModified)
                }
                else -> false
            }
        } catch (e: Exception) {
            return false
        }
    }

    fun downloadDb(token: String, filename: String, dbFile: File) {
        val out = FileOutputStream(dbFile)
        out.use {
            client(token).files().download("/$filename").download(it)
            it.flush()
        }
    }

    fun uploadDb(token: String, dbFile: File, filename: String) {
        client(token).files().uploadBuilder("/$filename")
                .withMode(WriteMode.OVERWRITE)
                .uploadAndFinish(FileInputStream(dbFile))
    }

    private fun client(token: String): DbxClientV2 {
        return DbxClientV2(config, token)
    }
}
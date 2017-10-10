package com.alexvit.simpleshopping.data.source

import android.util.Log
import com.alexvit.simpleshopping.data.models.Item
import com.alexvit.simpleshopping.data.source.dropbox.DropboxDatasource
import com.alexvit.simpleshopping.data.source.sql.ListsSqlDatasource
import io.reactivex.Observable
import java.io.File
import java.util.*

/**
 * Created by Aleksandrs Vitjukovs on 10/8/2017.
 */

class ListsRepository(private val sqlDatasource: ListsSqlDatasource,
                      private val dropboxDatasource: DropboxDatasource,
                      private val dbFile: File) {

    companion object {
        const val DB_NAME = "lists.db"
    }

    private val TAG = ListsRepository::class.java.simpleName

    fun getAllItems() = sqlDatasource.getAllItems().toObservable()

    fun insert(item: Item) = Observable.fromCallable {
        sqlDatasource.insert(item)
    }

    fun delete(item: Item) = Observable.fromCallable {
        sqlDatasource.delete(item)
    }

    fun getDropBoxToken(): String? = dropboxDatasource.getToken()

    fun setDropBoxToken(token: String) = dropboxDatasource.setToken(token)

    fun isRemoteDbNewer(): Observable<Boolean> {
        val token = getDropBoxToken()
        if (token == null) {
            return Observable.error<Boolean>(IllegalStateException("no dropbox token set"))
        } else {
            Log.d(TAG, "local modified = ${Date(dbFile.lastModified())}")
            return Observable.fromCallable {
                dropboxDatasource.isRemoteDbNewer(token, DB_NAME, Date(dbFile.lastModified()))
            }
        }
    }

    fun downloadDb(): Observable<Unit> {
        val token = getDropBoxToken()
        return if (token == null) {
            Observable.error<Unit>(IllegalStateException("no dropbox token set"))
        } else {
            Observable.fromCallable {
                dropboxDatasource.downloadDb(token, DB_NAME, dbFile)
            }
        }
    }

    fun uploadDb(): Observable<Unit> {
        val token = getDropBoxToken()
        return if (token != null) {
            Observable.fromCallable {
                dropboxDatasource.uploadDb(token, dbFile, DB_NAME)
            }
        } else {
            Log.e(TAG, "uploadDb(): token is null")
            Observable.empty()
        }
    }

}
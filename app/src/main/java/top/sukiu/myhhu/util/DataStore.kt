package top.sukiu.myhhu.util

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map


val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "UserData")

suspend fun addData(key: String, value: String) {
    val strKey = stringPreferencesKey(key)
    MyApplication.context.dataStore.edit {
        it[strKey] = value
    }
}

suspend fun readData(key: String): String {
    val strKey = stringPreferencesKey(key)
    val value = MyApplication.context.dataStore.data.map {
        it[strKey] ?: ""
    }
    return value.first()
}



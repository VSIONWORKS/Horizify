package com.horizon.horizify.extensions

import android.content.SharedPreferences
import android.util.Log
import com.google.gson.Gson
import com.horizon.horizify.base.SharedPreference


inline fun <reified T> SharedPreference.get(key: String, value: T): T {
    return when (value) {
        is String -> this.sharedPref.getString(key, value) as T
        is Int -> this.sharedPref.getInt(key, value) as T
        is Boolean -> this.sharedPref.getBoolean(key, value) as T
        is Float -> this.sharedPref.getFloat(key, value) as T
        is Long -> this.sharedPref.getLong(key, value) as T
        else -> {
            throw IllegalStateException("Unsupported Type: $value")
        }
    }
}

fun SharedPreferences.set(key: String, value: Any?) {
    when (value) {
        is String? -> edit { it.putString(key, value) }
        is Int -> edit { it.putInt(key, value.toInt()) }
        is Boolean -> edit { it.putBoolean(key, value) }
        is Float -> edit { it.putFloat(key, value.toFloat()) }
        is Long -> edit { it.putLong(key, value.toLong()) }
        else -> {
            Log.e("Unsupported Type:", "$value")
        }
    }
}

fun SharedPreference.saveObject(key: String, value: Any?) {
    val prefsEditor: SharedPreferences.Editor = sharedPref.edit()
    val gson = Gson()
    val json = gson.toJson(value)
    prefsEditor.putString(key, json)
    prefsEditor.commit()
}

fun SharedPreference.getObject(key: String): String {
    val gson = Gson()
    return sharedPref.getString(key, "") ?: ""
}

fun SharedPreference.save(KEY_NAME: String, value: Any) {
    this.sharedPref.set(KEY_NAME, value)
}

fun SharedPreferences.clear() {
    edit { it.clear() }
}

fun SharedPreferences.remove(key: String) {
    edit { it.remove(key) }
}

inline fun SharedPreferences.edit(operation: (SharedPreferences.Editor) -> Unit) {
    val editor = this.edit()
    operation(editor)
    editor.apply()
}
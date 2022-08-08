package com.horizon.horizify.base

import android.content.Context
import android.content.SharedPreferences
import com.horizon.horizify.extensions.remove
import com.horizon.horizify.utils.Constants.PREFS_NAME

class SharedPreference(val context: Context) {
    var sharedPref : SharedPreferences = context?.getSharedPreferences(PREFS_NAME,Context.MODE_PRIVATE)

    fun removeValue(KEY_NAME: String) {
        sharedPref.remove(KEY_NAME)
    }
}
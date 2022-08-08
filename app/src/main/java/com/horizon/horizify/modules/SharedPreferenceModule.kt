package com.horizon.horizify.modules

import android.content.Context
import android.content.SharedPreferences
import com.horizon.horizify.base.SharedPreference
import com.horizon.horizify.utils.Constants.PREFS_NAME
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val sharedPreferenceModules = module {
    single { SharedPreference(androidContext()) }
    single<SharedPreferences> { androidContext().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE) }
}
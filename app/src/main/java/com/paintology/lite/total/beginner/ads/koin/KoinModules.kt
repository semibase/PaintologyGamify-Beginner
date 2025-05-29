package com.paintology.lite.total.beginner.ads.koin

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import com.paintology.lite.total.beginner.ads.im.InternetManager
import com.paintology.lite.total.beginner.ads.config.RemoteConfiguration
import com.paintology.lite.total.beginner.ads.pref.SharedPreferenceUtils
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

private val managerModules = module {
    single { InternetManager(androidContext().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager) }
}

private val utilsModules = module {
    single {
        SharedPreferenceUtils(
            androidContext().getSharedPreferences(
                "app_preferences",
                Application.MODE_PRIVATE
            )
        )
    }
}

private val firebaseModule = module {
    single { RemoteConfiguration(get(), get()) }
}

private val adsModule = module {
}

val modulesList = listOf(utilsModules, managerModules, firebaseModule, adsModule)
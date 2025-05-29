package com.paintology.lite.total.beginner.ads.koin

import com.paintology.lite.total.beginner.ads.config.RemoteConfiguration
import com.paintology.lite.total.beginner.ads.im.InternetManager
import com.paintology.lite.total.beginner.ads.pref.SharedPreferenceUtils
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class DIComponent : KoinComponent {

    // Utils
    val sharedPreferenceUtils by inject<SharedPreferenceUtils>()

    // Managers
    val internetManager by inject<InternetManager>()

    // Remote Configuration
    val remoteConfiguration by inject<RemoteConfiguration>()


}
package com.paintology.lite.total.beginner.ads.callbacks

interface InterstitialOnLoadCallBack {
    fun onAdFailedToLoad(adError: String)
    fun onAdLoaded()
    fun onPreloaded()
}
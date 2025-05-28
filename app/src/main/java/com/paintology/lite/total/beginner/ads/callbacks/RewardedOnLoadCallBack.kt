package com.paintology.lite.total.beginner.ads.callbacks

interface RewardedOnLoadCallBack {
    fun onAdFailedToLoad(adError:String)
    fun onAdLoaded()
    fun onPreloaded()
}
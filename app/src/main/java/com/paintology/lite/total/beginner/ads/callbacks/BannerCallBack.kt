package com.paintology.lite.total.beginner.ads.callbacks

interface BannerCallBack {
    fun onAdFailedToLoad(adError: String)
    fun onAdLoaded()
    fun onAdImpression()
    fun onPreloaded()
    fun onAdClicked()
    fun onAdClosed()
    fun onAdOpened()
    fun onAdSwipeGestureClicked()
    fun onAdCloseFullScreenNative(){}
}
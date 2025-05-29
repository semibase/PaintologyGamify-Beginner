package com.paintology.lite.total.beginner.ads.constants


object AdsConstants {

    var isRewardedLoading = false
    var isOpenAdLoading = false
    var isNativeLoading = false
    var isNativeLoadingHome = false
    var isNativeLoadingFullScreen = false
    var isInterstitialLoading = false
    var isCollapsibleOpen = false

    fun reset() {
        isRewardedLoading = false
        isOpenAdLoading = false
        isCollapsibleOpen = false
        isInterstitialLoading = false
        isNativeLoading = false
        isNativeLoadingHome = false
        isNativeLoadingFullScreen = false
    }
}
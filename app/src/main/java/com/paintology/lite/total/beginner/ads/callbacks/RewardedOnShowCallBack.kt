package com.paintology.lite.total.beginner.ads.callbacks

interface RewardedOnShowCallBack {
    fun onAdClicked()
    fun onAdDismissedFullScreenContent()
    fun onAdFailedToShowFullScreenContent()
    fun onAdImpression()
    fun onAdShowedFullScreenContent()
    fun onUserEarnedReward()
}
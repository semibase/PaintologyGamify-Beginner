package com.paintology.lite.total.beginner.ads.consent

interface ConsentCallback {
    fun onAdsLoad(canRequestAd: Boolean) {}
    fun onConsentFormShow() {}
    fun onConsentFormDismissed() {}
    fun onPolicyStatus(required: Boolean) {}
}
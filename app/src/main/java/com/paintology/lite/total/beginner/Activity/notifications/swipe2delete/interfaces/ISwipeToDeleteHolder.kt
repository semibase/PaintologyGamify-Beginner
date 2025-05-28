package com.paintology.lite.total.beginner.Activity.notifications.swipe2delete.interfaces

import android.view.View

interface ISwipeToDeleteHolder<K> {

    var pendingDelete: Boolean

    val topContainer: View

    var key: String
}
package com.paintology.lite.total.beginner.Activity.notifications.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Data(
    val target_id: String = "",
    val target_name: String = "",
    val target_type: String = ""
) : Parcelable
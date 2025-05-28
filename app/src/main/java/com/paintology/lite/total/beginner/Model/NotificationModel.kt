package com.paintology.lite.total.beginner.Model

import com.paintology.lite.total.beginner.util.NotificationType

data class NotificationModel(
    val title: String?,
    val text: String?,
    val type: NotificationType,
    val postId: String?,
    val userId: String?
)

package com.salomohutapea.fcmtutorial

import com.google.gson.annotations.SerializedName

enum class NotificationType {
    COMPLEX,
    MESSAGE,
}

data class NotificationData(
    @SerializedName("id") val id: Int,
    @SerializedName("type") val type: NotificationType,
    @SerializedName("title") val title: String,
    @SerializedName("subTitle") val subTitle: String?,
    @SerializedName("img") val img: String?,
    @SerializedName("contentTitle") val contentTitle: String?,
    @SerializedName("content") val content: String
)
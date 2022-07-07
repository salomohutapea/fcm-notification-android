package com.salomohutapea.fcmtutorial

import com.google.gson.annotations.SerializedName

enum class NotificationType {
    COMPLEX,
    MESSAGE,
}

data class NotificationData(
    @SerializedName("id") val id: Int? = null,
    @SerializedName("type") val type: NotificationType? = NotificationType.COMPLEX,
    @SerializedName("title") val title: String,
    @SerializedName("subTitle") val subTitle: String?,
    @SerializedName("img") val img: String? = null,
    @SerializedName("contentTitle") val contentTitle: String?,
    @SerializedName("content") val content: String?
)
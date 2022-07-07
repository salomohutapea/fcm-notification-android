package com.salomohutapea.fcmtutorial

import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.google.gson.Gson

class FCMService : FirebaseMessagingService() {

    override fun onMessageReceived(message: RemoteMessage) {
        val text = message.data.getOrDefault("data", "")
        val data = Gson().fromJson(text, NotificationData::class.java)

        NotificationUtil.createNotificationChannel(this)

        NotificationUtil.buildNotification(data, this)
        super.onMessageReceived(message)
    }

    override fun onNewToken(token: String) {
        // Can be used to push notification to specific user group in BE
        // sendRegistrationToServer(token)
        Log.d("TOKEN", token)
        super.onNewToken(token)
    }
}
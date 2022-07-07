package com.salomohutapea.fcmtutorial

import android.util.Log
import com.google.firebase.database.FirebaseDatabase
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
        val database =
            FirebaseDatabase.getInstance("https://fcm-tutorial-1a981-default-rtdb.asia-southeast1.firebasedatabase.app")
        val myRef = database.getReference("message")

        val key = myRef.child("posts").push().key

        if (key != null) {
            myRef.child(key).child("name").setValue(token)
        }
        super.onNewToken(token)
    }
}
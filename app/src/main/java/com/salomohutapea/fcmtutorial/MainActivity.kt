package com.salomohutapea.fcmtutorial

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.messaging.FirebaseMessaging


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        FirebaseMessaging.getInstance().token.addOnSuccessListener { token: String ->
            if (!TextUtils.isEmpty(token)) {
                Log.d("TOKEN", token)
                val database =
                    FirebaseDatabase.getInstance("https://fcm-tutorial-1a981-default-rtdb.asia-southeast1.firebasedatabase.app")
                val myRef = database.getReference("message")

                val key = myRef.child("posts").push().key

                if (key != null) {
                    myRef.child(key).child("name").setValue(token)
                }
            } else {
                Log.w("TOKEN", "token should not be null...")
            }
        }
    }
}
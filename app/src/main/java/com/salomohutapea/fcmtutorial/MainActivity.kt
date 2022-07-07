package com.salomohutapea.fcmtutorial

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.messaging.FirebaseMessaging
import android.text.TextUtils
import com.google.android.gms.tasks.*
import java.lang.Exception


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        FirebaseMessaging.getInstance().token.addOnSuccessListener { token: String ->
            if (!TextUtils.isEmpty(token)) {
                Log.d("TOKEN", token)
            } else {
                Log.w("TOKEN", "token should not be null...")
            }
        }
    }
}
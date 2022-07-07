package com.salomohutapea.fcmtutorial

import android.R.attr.data
import android.os.Bundle
import android.webkit.URLUtil
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.salomohutapea.fcmtutorial.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val binding: ActivityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnsubmit.setOnClickListener {
            val imgUrl = binding.etImg.text.toString().run {
                if (URLUtil.isValidUrl(this)) this
                else "https://cdn.britannica.com/91/181391-050-1DA18304/cat-toes-paw-number-paws-tiger-tabby.jpg?q=60"
            }
            RestApiService.sendMessage(
                NotificationData(
                    null,
                    NotificationType.COMPLEX,
                    binding.etTitle.text.toString(),
                    binding.etSubtitle.text.toString(),
                    imgUrl,
                    binding.etContenttitle.text.toString(),
                    binding.etContent.text.toString()
                ),
            ) {

            }
        }
    }
}
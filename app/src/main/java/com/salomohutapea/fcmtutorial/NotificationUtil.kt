package com.salomohutapea.fcmtutorial

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Build
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.target.NotificationTarget
import com.bumptech.glide.request.transition.Transition


object NotificationUtil {
    private const val CHANNEL_ID = "notification_id"
    private const val CHANNEL_NAME = "Notification"
    private const val CHANNEL_IMPORTANCE = NotificationManager.IMPORTANCE_HIGH

    fun createNotificationChannel(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationManager = getNotificationManager(context)
            var isChannelCreated = false

            for (channel in notificationManager.notificationChannels) {
                if (channel.id == CHANNEL_ID) isChannelCreated = true
            }

            if (!isChannelCreated) {
                val channel =
                    NotificationChannel(
                        CHANNEL_ID,
                        CHANNEL_NAME,
                        CHANNEL_IMPORTANCE
                    )
                notificationManager.createNotificationChannel(channel)
            }
        }
    }

    private fun getNotificationManager(appContext: Context): NotificationManager =
        appContext.getSystemService(NotificationManager::class.java)

    fun buildNotification(data: NotificationData, context: Context) {
        lateinit var notification: Notification

        if (data.type == NotificationType.COMPLEX) {
            val layout = RemoteViews(context.packageName, R.layout.notification_one)

            notification = NotificationCompat.Builder(context, CHANNEL_ID).apply {
                setContentTitle(data.title)
                setSmallIcon(R.drawable.ic_launcher_background)
                setAutoCancel(false)
                setStyle(NotificationCompat.DecoratedCustomViewStyle())
                setCustomContentView(layout)
            }.build()

            generateOneContent(context, notification, data, layout)
        } else {
            notification = NotificationCompat.Builder(context, CHANNEL_ID).apply {
                setContentTitle(data.title)
                setContentText(data.content)
                setSmallIcon(R.drawable.ic_launcher_background)
                setAutoCancel(true)
            }.build()

            getNotificationManager(context).notify(data.id, notification)
        }
    }

    private fun generateOneContent(
        context: Context,
        notification: Notification,
        data: NotificationData,
        layout: RemoteViews
    ) {
        layout.setTextViewText(R.id.one_title, data.title)
        layout.setTextViewText(R.id.one_subtitle, data.subTitle)
        layout.setTextViewText(R.id.one_contenttitle, data.contentTitle)
        layout.setTextViewText(R.id.one_content, data.content)

        val oneImage = NotificationTarget(
            context,
            R.id.one_img,
            layout,
            notification,
            data.id
        )

        Glide.with(context)
            .asBitmap()
            .load(data.img)
            .into(object : CustomTarget<Bitmap>(200, 200) {
                override fun onLoadCleared(placeholder: Drawable?) {
                    TODO("Not yet implemented")
                }

                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                    oneImage.onResourceReady(resource, transition)
                    getNotificationManager(context).notify(data.id, notification)
                }
            })
    }
}
package com.salomohutapea.fcmtutorial

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
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

        val notificationIntent = Intent(context, MainActivity::class.java)

        notificationIntent.flags =
            Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP

        val intent =
            PendingIntent.getActivity(context, 0, notificationIntent, PendingIntent.FLAG_IMMUTABLE)

        if (data.type == NotificationType.COMPLEX) {
            val layout = RemoteViews(context.packageName, R.layout.notification_one)
            val layoutHidden =
                RemoteViews(context.packageName, R.layout.notification_one_hidden)

            notification = NotificationCompat.Builder(context, CHANNEL_ID).apply {
                setContentTitle(data.title)
                setSmallIcon(R.drawable.ic_launcher_background)
                setAutoCancel(true)
                setStyle(NotificationCompat.DecoratedCustomViewStyle())
                setCustomContentView(layoutHidden)
                setCustomBigContentView(layout)
                setContentIntent(intent)
            }.build()

            generateNotificationContent(context, notification, data, layout, layoutHidden)
        } else {
            notification = NotificationCompat.Builder(context, CHANNEL_ID).apply {
                setContentTitle(data.title)
                setContentText(data.content)
                setSmallIcon(R.drawable.ic_launcher_background)
                setAutoCancel(true)
                setContentIntent(intent)
            }.build()

            data.id?.let { getNotificationManager(context).notify(it, notification) }
        }
    }

    private fun generateNotificationContent(
        context: Context,
        notification: Notification,
        data: NotificationData,
        layout: RemoteViews,
        layoutHidden: RemoteViews
    ) {
        layout.setTextViewText(R.id.one_title, data.title)
        layout.setTextViewText(R.id.one_subtitle, data.subTitle)
        layout.setTextViewText(R.id.one_contenttitle, data.contentTitle)
        layout.setTextViewText(R.id.one_content, data.content)

        layoutHidden.setTextViewText(
            R.id.one_content_hidden,
            data.subTitle
        )
        layoutHidden.setTextViewText(R.id.one_title_hidden, data.title)

        val oneImage = data.id?.let {
            NotificationTarget(
                context,
                R.id.one_img,
                layout,
                notification,
                it
            )
        }

        Glide.with(context)
            .asBitmap()
            .load(data.img)
            .fitCenter()
            .into(object : CustomTarget<Bitmap>(200, 200) {
                override fun onLoadCleared(placeholder: Drawable?) {
                }

                override fun onResourceReady(
                    resource: Bitmap,
                    transition: Transition<in Bitmap>?
                ) {
                    oneImage?.onResourceReady(resource, transition)
                    data.id?.let { getNotificationManager(context).notify(it, notification) }
                }
            })

    }
}
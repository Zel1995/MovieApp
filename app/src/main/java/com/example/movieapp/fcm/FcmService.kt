package com.example.movieapp.fcm

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import com.example.movieapp.MainActivity
import com.example.movieapp.R
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class FcmService: FirebaseMessagingService() {

    companion object{
        private const val CHANNEL_ID ="CHANNEL_ID"
    }
    private val notificationManager by lazy { getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager }
    override fun onNewToken(token: String) {
        super.onNewToken(token)
    }

    override fun onCreate() {
        super.onCreate()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel(CHANNEL_ID,"Channel name",NotificationManager.IMPORTANCE_DEFAULT).also {
                notificationManager.createNotificationChannel(it)
            }
        }
    }
    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        message.notification?.apply{showNotification(this)}
    }

    private fun showNotification(notification: RemoteMessage.Notification) {
        val intent = Intent(this,MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivities(this,0, arrayOf(intent),PendingIntent.FLAG_UPDATE_CURRENT)

        NotificationCompat.Builder(this, CHANNEL_ID).apply {
            setContentTitle(notification.title)
            setContentText(notification.body)
            setContentIntent(pendingIntent)
            setSmallIcon(R.drawable.ic_fav)
            setAutoCancel(true)
        }.also {
            notificationManager.notify(1,it.build())
        }
    }
}
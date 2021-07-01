package com.example.movieapp.domain

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Context.NOTIFICATION_SERVICE
import android.content.Intent
import android.os.Build
import android.widget.Toast
import androidx.core.app.NotificationCompat
import com.example.movieapp.R

class AirplaneModeReceiver : BroadcastReceiver() {
    //CONNECTIVITY is Deprecated ,поэтому сделал с AIRPLANE MODE
    companion object {
        private const val CHANNEL_ID = "10"
    }

    override fun onReceive(context: Context?, intent: Intent?) {

        if (intent?.action == Intent.ACTION_AIRPLANE_MODE_CHANGED) {
            Toast.makeText(context, "airplane mode changed", Toast.LENGTH_SHORT).show()


            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val importance = NotificationManager.IMPORTANCE_DEFAULT
                val mChannel = NotificationChannel(CHANNEL_ID, "name", importance)
                val notificationManager =
                    context?.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
                notificationManager.createNotificationChannel(mChannel)
            }
            val builder = context?.let {
                NotificationCompat.Builder(it, CHANNEL_ID)
                    .setSmallIcon(R.drawable.ic_baseline_grade_24)
                    .setContentTitle("title").setContentText("text")
            }
            val manager = context?.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            manager.notify(1, builder?.build())
        }
    }
}
package io.tech4fun.lanorganizer.services

import android.app.Notification
import android.app.NotificationChannel
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.os.Messenger
import android.widget.Toast
import androidx.annotation.RequiresApi
import io.tech4fun.lanorganizer.MainActivity

class MyForegroundService : Service() {

    private lateinit var mMessenger: Messenger

    override fun onCreate() {
        super.onCreate()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val ret = super.onStartCommand(intent, flags, startId)

        // If the notification supports a direct reply action, use
// PendingIntent.FLAG_MUTABLE instead.
        val pendingIntent: PendingIntent =
            Intent(this, MainActivity::class.java).let { notificationIntent ->
                PendingIntent.getActivity(this, 0, notificationIntent,
                    PendingIntent.FLAG_IMMUTABLE)
            }

        val notification: Notification = Notification.Builder(this, NotificationChannel.DEFAULT_CHANNEL_ID)
            .setContentTitle("My Title")
            .setContentText("Hello from service")
            .setContentIntent(pendingIntent)
            .build()

        // Notification ID cannot be 0.
        startForeground(1, notification)

        return ret
    }

    override fun onBind(intent: Intent?): IBinder? {
        Toast.makeText(applicationContext, "binding foreground", Toast.LENGTH_SHORT).show()
        mMessenger = Messenger(MyBoundService.IncomingHandler(this))
        return mMessenger.binder
    }
}
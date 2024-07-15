package com.example.livelocation

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build

class RunningApp: Application() {

//    Creating a notification channel when our app is originally launched, i.e. just once when the app boots up
    override fun onCreate() {
        super.onCreate()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                "location",
                "Location",
                //            passing importance of notification
                NotificationManager.IMPORTANCE_LOW
            )

//            Creating Channel, Getting system service i.e. service that comes directly from android OS
//            android provides notificationManager service for notifications
            val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE)
                    as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
}
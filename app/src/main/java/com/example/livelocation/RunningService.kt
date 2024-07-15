package com.example.livelocation

import android.annotation.SuppressLint
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

//Service is an android componenet which inherits from context, so when needing context, you can use the "this" keyword
//When this is used, the service context is limited to the lifetime of the service

class RunningService: Service() {
//    Here we now specify what kind of service it is

    private val serviceScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)
    private lateinit var locationClient: LocationClient

//    IBinder is used to create a bound service, basically
    //    where other components will want to communicate with the service
    override fun onBind(intent: Intent?): IBinder? {
        return null // This is just saying nothing can bind to this service
    }

    override fun onCreate() {
        super.onCreate()
        locationClient = DefaultLocationClient(
            applicationContext,
            LocationServices.getFusedLocationProviderClient(applicationContext)
        )
    }

    //        Defining the commands that our activity or other android components can send to the service

//    This is triggered whenever another android component sends an intent to this running service
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when(intent?.action) {
            ACTION_START -> start()
            ACTION_STOP -> stop()
        }
        return super.onStartCommand(intent, flags, startId)
    }

    @SuppressLint("ForegroundServiceType")
//    private fun start() {
//    //        To make it a foreground service, it needs to come with a persistent
//    //        notification, otherwise the user won't be aware that it's doing something so we need to call startforeground
//        val notification = NotificationCompat.Builder(this, "running_channel")
//            .setSmallIcon(R.drawable.ic_launcher_foreground)
//            .setContentTitle("Running is active")
//            .setContentText("Elapsed time: 00:50")
//            .build()
//        startForeground(1, notification)
//
//    }
    private fun start() {
        val notification = NotificationCompat.Builder(this, "location")
            .setContentTitle("Tracking location...")
            .setContentText("Location: null")
            .setSmallIcon(R.drawable.ic_launcher_background)
            .setOngoing(true)

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        locationClient
            .getLocationUpdates(10000L)
            .catch { e -> e.printStackTrace() }
            .onEach { location ->
                val lat = location.latitude.toString()
                val long = location.longitude.toString()
                val updatedNotification = notification.setContentText(
                    "Location: ($lat, $long)"
                )
                notificationManager.notify(1, updatedNotification.build())
            }
            .launchIn(serviceScope)

        startForeground(1, notification.build())
    }

    private fun stop() {
        stopForeground(STOP_FOREGROUND_REMOVE);
        stopSelf()
    }

    override fun onDestroy() {
        super.onDestroy()
        serviceScope.cancel()
    }

//    Specifying the different actions we can have for this service
//    enum class Actions {
//        START, STOP
//    }
companion object {
    const val ACTION_START = "ACTION_START"
    const val ACTION_STOP = "ACTION_STOP"
}
}
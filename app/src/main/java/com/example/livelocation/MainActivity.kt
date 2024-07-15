package com.example.livelocation

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.livelocation.ui.theme.LivelocationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        Permission request
////        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
//            ActivityCompat.requestPermissions(
//                this,
//                arrayOf(
////                    Manifest.permission.POST_NOTIFICATIONS,
//                    Manifest.permission.ACCESS_COARSE_LOCATION,
//                    Manifest.permission.ACCESS_FINE_LOCATION,
//                ),
//                0
//            )
//        }

        setContent {
            LivelocationTheme{

//                Column(
//                    modifier = Modifier.fillMaxSize(),
//                    horizontalAlignment = Alignment.CenterHorizontally,
//                    verticalArrangement = Arrangement.Center
//                ) {
//                    Button(onClick = {
//                        Intent(applicationContext, RunningService::class.java).apply {
//                            action = RunningService.ACTION_START
//                            startService(this)
//                        }
//                    }) {
//                        Text(text = "Start")
//                    }
//
//                    Button(onClick = {
//                        Intent(applicationContext, RunningService::class.java).apply {
//                            action = RunningService.ACTION_STOP
//                            startService(this)
//                        }
//                    }) {
//                        Text(text = "Stop")
//                    }
//                }

                PermissionBox()

            }
        }
    }
}

//@Composable
//fun RequestPermissions() {
//    val context = LocalContext.current
//    var permissionGranted by remember { mutableStateOf(false) }
//
//    val launcher = rememberLauncherForActivityResult(
//        contract = ActivityResultContracts.RequestMultiplePermissions()
//    ) { permissions ->
//        permissionGranted = permissions[Manifest.permission.ACCESS_FINE_LOCATION] == true &&
//                permissions[Manifest.permission.ACCESS_COARSE_LOCATION] == true &&
//                permissions[if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
//                    Manifest.permission.FOREGROUND_SERVICE_LOCATION
//                } else {
//                    TODO("VERSION.SDK_INT < UPSIDE_DOWN_CAKE")
//                }] == true
//    }
//
//    LaunchedEffect(key1 = Unit) {
//        if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
//            != PackageManager.PERMISSION_GRANTED ||
//            ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION)
//            != PackageManager.PERMISSION_GRANTED ||
//            ContextCompat.checkSelfPermission(context, Manifest.permission.FOREGROUND_SERVICE_LOCATION)
//            != PackageManager.PERMISSION_GRANTED) {
//            launcher.launch(arrayOf(
//                Manifest.permission.ACCESS_FINE_LOCATION,
//                Manifest.permission.ACCESS_COARSE_LOCATION,
//                Manifest.permission.FOREGROUND_SERVICE_LOCATION
//            ))
//        } else {
//            permissionGranted = true
//        }
//    }
//
//    if (permissionGranted) {
//        StartForegroundServiceButton(context)
//    } else {
//        Text("Location permissions are required for this app to function properly.")
//    }
//}
//
//@Composable
//fun StartForegroundServiceButton(context: Context) {
//    Column(
//        modifier = Modifier.fillMaxSize(),
//        horizontalAlignment = Alignment.CenterHorizontally,
//        verticalArrangement = Arrangement.Center
//    ) {
//        Button(onClick = {
//            Intent(context, RunningService::class.java).apply {
//                action = RunningService.ACTION_START
//                context.startService(this)
//            }
//        }) {
//            Text("Start")
//        }
//
//        Button(onClick = {
//            Intent(context, RunningService::class.java).apply {
//                action = RunningService.ACTION_STOP
//                context.startService(this)
//            }
//        }) {
//            Text(text = "Stop")
//        }
//    }
//
//}
//
//@Preview(showBackground = true)
//@Composable
//fun DefaultPreview() {
//    LivelocationTheme{
//        RequestPermissions()
//    }
//}
package com.example.livelocation

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.provider.Settings
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat


@Composable
fun PermissionBox(
    viewModel: PermissionViewModel = PermissionViewModel()
) {
    val context = LocalContext.current

    val showDialog = viewModel.showDialog.collectAsState().value
    val launchAppSettings = viewModel.launchAppSettings.collectAsState().value

    val permissions = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
        arrayOf(
            android.Manifest.permission.ACCESS_FINE_LOCATION,
            android.Manifest.permission.ACCESS_COARSE_LOCATION,
            android.Manifest.permission.FOREGROUND_SERVICE_LOCATION
        )
    } else {
        TODO("VERSION.SDK_INT < UPSIDE_DOWN_CAKE")
    }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions() ,
        onResult = {result ->
            permissions.forEach { permission ->
                if(result[permission] == false) {
                    if(!ActivityCompat.shouldShowRequestPermissionRationale(context as Activity, permission)){
                        viewModel.updateLaunchAppSettings(true)
                    }
                    viewModel.updateShowDialog(true)
                }
            }
        }
    )

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Button(
            onClick = {
                permissions.forEach { permission ->
                    val isGranted = ContextCompat.checkSelfPermission(context, permission) ==
                            PackageManager.PERMISSION_GRANTED

                    if(!isGranted) {
                        if(ActivityCompat.shouldShowRequestPermissionRationale(context as Activity, permission)){
                            viewModel.updateShowDialog(true)
                        }else {
                            launcher.launch(permissions)
                        }
                    }

                }
            }
        ) {
            Text(text = "Request Permission")
        }
    }

    if(showDialog) {
        PermissionDialog(
            onDismiss = { viewModel.updateShowDialog(false) },
            onConfirm = {
                viewModel.updateShowDialog(false)

                if(launchAppSettings){

                    Intent(
                        Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                        Uri.fromParts("package", context.packageName, null)
                    ).also {
                        context.startActivity(it)
                    }

                    viewModel.updateLaunchAppSettings(false)
                }else {
                    launcher.launch(permissions)
                }

            }
        )
    }


}

@Composable
fun PermissionDialog(
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
) {
    AlertDialog(
        modifier = Modifier.fillMaxWidth(),
//        icon = {
//            Icon(icon, contentDescription = "Example Icon")
//        },
        title = {
            Text(text = "Location Permission is needed")
        },
        text = {
            Text(text = "To function properly, this app needs to access your location")
        },
        onDismissRequest = {
            onDismiss()
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onConfirm()
                }
            ) {
                Text("Confirm")
            }
        },
//        dismissButton = {
//            TextButton(
//                onClick = {
//                    onDismiss()
//                }
//            ) {
//                Text("Dismiss")
//            }
//        }
    )
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
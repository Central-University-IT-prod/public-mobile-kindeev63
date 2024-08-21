package com.knomster.mobile_kindeev63.presentation

import android.Manifest
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale

@Composable
@OptIn(ExperimentalPermissionsApi::class)
fun GetLocationPermission(
    whenGranted: @Composable () -> Unit,
    whenNotGranted: @Composable () -> Unit
) {
    val permissionState = rememberPermissionState(Manifest.permission.ACCESS_FINE_LOCATION)
    if (permissionState.status.isGranted) {
        whenGranted()
    } else {
        if (permissionState.status.shouldShowRationale) {
            whenNotGranted()
        } else {
            LaunchedEffect(permissionState) {
                permissionState.launchPermissionRequest()
            }
        }
    }
}
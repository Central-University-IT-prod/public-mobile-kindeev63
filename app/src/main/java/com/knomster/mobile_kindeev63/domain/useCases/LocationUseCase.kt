package com.knomster.mobile_kindeev63.domain.useCases

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import androidx.core.content.ContextCompat
import com.google.android.gms.location.LocationServices

class LocationUseCase(private val context: Context) {
    fun getLocation(
        getPermissionQuery: (() -> Unit)? = null,
        onGetLocation: (Location) -> Unit
    ) {
        val permissionState = ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_FINE_LOCATION
        )
        if (permissionState == PackageManager.PERMISSION_GRANTED) {
            val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
            fusedLocationClient.lastLocation.addOnSuccessListener { onGetLocation(it) }
        } else {
            getPermissionQuery?.let { it() }
        }
    }
}
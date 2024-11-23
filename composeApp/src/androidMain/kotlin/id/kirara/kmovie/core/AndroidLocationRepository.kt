package id.kirara.kmovie.core

import android.annotation.SuppressLint
import android.content.Context
import com.google.android.gms.location.LocationServices
import id.kirara.kmovie.domain.location.DeviceLocation
import id.kirara.kmovie.domain.location.LocationRepository
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

internal class AndroidLocationRepository(
    private val context: Context
) : LocationRepository {

    private val fusedLocationClient by lazy {
        LocationServices.getFusedLocationProviderClient(context)
    }

    @SuppressLint("MissingPermission")
    override suspend fun getCurrentLocation(): DeviceLocation = suspendCoroutine { continuation ->
        fusedLocationClient.lastLocation
            .addOnSuccessListener {location ->
                if (location == null) return@addOnSuccessListener
                continuation.resume(
                    DeviceLocation(
                        latitude = location.latitude,
                        longitude = location.longitude,
                    )
                )
            }.addOnFailureListener {
                continuation.resumeWithException(it)
            }
    }

}
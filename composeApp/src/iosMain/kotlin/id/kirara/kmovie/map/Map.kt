package id.kirara.kmovie.map

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.interop.UIKitView
import id.kirara.kmovie.domain.location.DeviceLocation
import id.kirara.kmovie.ui.scene.map.Cinema
import id.kirara.kmovie.ui.scene.map.MapUiState
import id.kirara.kmovie.utils.asUiImage
import kmovie.composeapp.generated.resources.Res
import kmovie.composeapp.generated.resources.ic_maps_marker
import kmovie.composeapp.generated.resources.ic_maps_marker_user
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.useContents
import platform.CoreLocation.CLLocationCoordinate2DMake
import platform.MapKit.MKAnnotationProtocol
import platform.MapKit.MKAnnotationView
import platform.MapKit.MKCoordinateRegionMakeWithDistance
import platform.MapKit.MKMapView
import platform.MapKit.MKMapViewDelegateProtocol
import platform.MapKit.MKPointAnnotation
import platform.MapKit.MKUserLocation
import platform.UIKit.UIImage
import platform.darwin.NSObject

@OptIn(ExperimentalForeignApi::class)
@Composable
actual fun Map(
    modifier: Modifier,
    uiState: MapUiState,
    onMarkerClick: (Cinema?) -> Unit,
    onPositionChange: (DeviceLocation) -> Unit
) {

    var location by remember {
        mutableStateOf(
            CLLocationCoordinate2DMake(
                uiState.lastLocation?.latitude ?: 0.0,
                uiState.lastLocation?.longitude ?: 0.0
            )
        )
    }

    LaunchedEffect(uiState.lastLocation) {
        uiState.lastLocation?.let {
            location = CLLocationCoordinate2DMake(
                uiState.lastLocation.latitude,
                uiState.lastLocation.longitude
            )
        }
    }

    val annotation = remember {
        MKPointAnnotation(
            location,
            title = null,
            subtitle = null
        )
    }

    val isMoved = remember { mutableStateOf(true) }

    val mkMapView = remember {
        MKMapView().apply {
            addAnnotation(annotation)
            setUserInteractionEnabled(true)
            showsUserLocation = true
            pitchEnabled = true
            scrollEnabled = true
            zoomEnabled = true
        }
    }

    val userLocation = Res.drawable.ic_maps_marker_user.asUiImage()
    val marker = Res.drawable.ic_maps_marker.asUiImage()

    val delegate = remember {
        MKDelegate(
            userLocation,
            marker,
            onMove = { onMove ->
                isMoved.value = onMove
                mkMapView.centerCoordinate.useContents {
                    onPositionChange.invoke(DeviceLocation(latitude, longitude))
                }
            }, onAnnotationClicked = { annotation ->
                if (annotation != null) {
                    val deviceLocation = annotation.coordinate.useContents {
                        DeviceLocation(latitude, longitude)
                    }
                    onMarkerClick.invoke(
                        Cinema(
                            annotation.title.orEmpty(),
                            annotation.subtitle.orEmpty(),
                            deviceLocation
                        )
                    )
                } else {
                    onMarkerClick(null)
                }
            })
    }

    LaunchedEffect(isMoved) {
        if (isMoved.value) {
            mkMapView.centerCoordinate.useContents {
                onPositionChange.invoke(DeviceLocation(latitude, longitude))
            }
        }
    }

    UIKitView(
        modifier = modifier.fillMaxSize(),
        interactive = true,
        factory = {
            mkMapView
        }, update = { view ->
            mkMapView.setRegion(
                MKCoordinateRegionMakeWithDistance(
                    centerCoordinate = location,
                    10_000.0, 10_000.0
                ),
                animated = true
            )
            mkMapView.setDelegate(delegate)

            val pins = uiState.cinemaList.map { item ->
                val pin = MKPointAnnotation()
                val coordinates = item.location

                pin.setCoordinate(
                    CLLocationCoordinate2DMake(
                        coordinates.latitude,
                        coordinates.longitude
                    )
                )
                pin.setTitle(item.name)
                pin.setSubtitle(item.description)
                pin
            }
            mkMapView.addAnnotations(pins)
        }
    )
}

@Suppress("CONFLICTING_OVERLOADS", "PARAMETER_NAME_CHANGED_ON_OVERRIDE")
private class MKDelegate(
    private val userLocationImage: UIImage?,
    private val markerImage: UIImage?,
    private val onAnnotationClicked: (MKAnnotationProtocol?) -> Unit,
    private val onMove: (Boolean) -> Unit
) : NSObject(), MKMapViewDelegateProtocol {

    override fun mapView(mapView: MKMapView, regionDidChangeAnimated: Boolean) {
        onMove(regionDidChangeAnimated)
    }

    override fun mapView(
        mapView: MKMapView,
        viewForAnnotation: MKAnnotationProtocol
    ): MKAnnotationView {
        return if (viewForAnnotation is MKUserLocation) {
            mapView.getOrCreateAnnotation(viewForAnnotation, "user").apply {
                image = userLocationImage
            }
        } else {
            mapView.getOrCreateAnnotation(viewForAnnotation, "custom").apply {
                image = markerImage
                canShowCallout = false
            }
        }
    }

    override fun mapView(mapView: MKMapView, didSelectAnnotationView: MKAnnotationView) {
        if (didSelectAnnotationView.annotation !is MKUserLocation)
            onAnnotationClicked(didSelectAnnotationView.annotation)
    }


    private fun MKMapView.getOrCreateAnnotation(
        viewForAnnotation: MKAnnotationProtocol,
        identifier: String
    ): MKAnnotationView {
        return dequeueReusableAnnotationViewWithIdentifier(identifier)
            ?: MKAnnotationView(viewForAnnotation, identifier)
    }
}
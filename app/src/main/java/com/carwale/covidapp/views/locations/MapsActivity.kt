package com.carwale.covidapp.views.locations

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.provider.Settings
import android.util.Log
import android.view.View
import androidx.core.app.ActivityCompat
import com.carwale.covidapp.R
import com.carwale.covidapp.base.BaseActivity
import com.carwale.covidapp.databinding.ActivityMapsBinding
import com.carwale.covidapp.utils.Constants
import com.carwale.covidapp.utils.dialogs.DialogBuilder
import com.carwale.covidapp.utils.dialogs.DialogListener
import com.carwale.covidapp.utils.shared_prefrence.SharedPref
import com.carwale.covidapp.views.dashboard.DashboardActivity
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.Autocomplete
import com.google.android.libraries.places.widget.AutocompleteActivity
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode
import com.google.android.material.snackbar.Snackbar
import java.io.IOException

class MapsActivity : BaseActivity<ActivityMapsBinding, MapViewModel>(), OnMapReadyCallback,
    GoogleMap.OnMarkerClickListener,
    GoogleMap.OnCameraMoveListener, GoogleMap.OnCameraIdleListener {
    private val REQUESTED_PERMISSION = Manifest.permission.ACCESS_FINE_LOCATION
    private val REQUEST_CODE = 3
    private val AUTOCOMPLETE_REQUEST_CODE = 1

    private lateinit var map: GoogleMap
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    var fromApp = false

    override fun getLayoutId(): Int = R.layout.activity_maps
    override fun getViewModel(): Class<MapViewModel> = MapViewModel::class.java

    override fun onBinding() {
        fromApp = intent.getBooleanExtra("fromApp", false)
        Places.initialize(applicationContext, getString(R.string.google_maps_key))
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        mBinding.customeSearch.setOnClickListener {
            // Set the fields to specify which types of place data to
            // return after the user has made a selection.
            val fields =
                listOf(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG, Place.Field.ADDRESS)
            // Start the autocomplete intent.
            val intent =
                Autocomplete.IntentBuilder(AutocompleteActivityMode.FULLSCREEN, fields).build(this)
            startActivityForResult(intent, AUTOCOMPLETE_REQUEST_CODE)
        }
        mBinding.setLocation.setOnClickListener {
            if (SharedPref.getInstance().getStringPreference(Constants.Location.NAME)
                    .isNotEmpty()
            ) {
                if (fromApp) {
                    finish()
                } else {
                    val intent = Intent(this, DashboardActivity::class.java)
                    startActivity(intent)
                    finishAffinity()
                }
            } else {
                Snackbar.make(mBinding.setLocation, "Select Location first", Snackbar.LENGTH_SHORT)
                    .show()
            }
        }
        mBinding.btnDetectLocation.setOnClickListener {
            detectLocation()
        }
    }

    override fun viewModelListener() {

    }

    override fun onCameraMove() {
        map.clear()
        mBinding.imgLocationPinUp.visibility = View.VISIBLE
//        getAddress(map.cameraPosition.target)
    }

    override fun onCameraIdle() {
        // hiding imageView
        mBinding.imgLocationPinUp.visibility = View.GONE
        // customizing map marker with a custom icon
        // and place it on the current map camera position
        val markerOptions = MarkerOptions().position(map.cameraPosition.target)
            .icon(BitmapDescriptorFactory.fromResource(R.drawable.image_pin))
        map.addMarker(markerOptions)
        getAddress(map.cameraPosition.target)
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
        map.setMapStyle(MapStyleOptions.loadRawResourceStyle(this, R.raw.map_style))
        map.uiSettings.isZoomControlsEnabled = true
        map.setOnMarkerClickListener(this)
        map.setOnCameraIdleListener(this)
        map.setOnCameraMoveListener(this)
        plotMap()
    }

    override fun onResume() {
        super.onResume()
        if (this::map.isInitialized) {
            plotMap()
        }
    }

    fun plotMap() {
        val pref = SharedPref.getInstance()
        val locality = pref.getStringPreference(Constants.Location.NAME)
        val address = pref.getStringPreference(Constants.Location.ADDRESS)
        val lat = pref.getDoublePreference(Constants.Location.LAT)
        val lng = pref.getDoublePreference(Constants.Location.LONG)
        if (locality.isNotEmpty()) {
            mBinding.tvLocality.apply {
                text = locality
                visibility = View.VISIBLE
            }
            mBinding.tvAddress.apply {
                text = address
                visibility = View.VISIBLE
            }
            map.animateCamera(CameraUpdateFactory.newLatLngZoom(LatLng(lat, lng), 15f))
        } else {
            detectLocation()
        }
    }

    override fun onMarkerClick(p0: Marker?) = false

    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 3
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == AUTOCOMPLETE_REQUEST_CODE) {
            when (resultCode) {
                Activity.RESULT_OK -> {
                    data?.let {
                        val place = Autocomplete.getPlaceFromIntent(data)
                        if (place.latLng != null && place.name != null && place.address != null) {
                            getAddress(place.latLng!!)
                            map.animateCamera(CameraUpdateFactory.newLatLngZoom(place.latLng, 15f))
                        }
                    }
                }
                AutocompleteActivity.RESULT_ERROR -> {
                    data?.let {
                        val status = Autocomplete.getStatusFromIntent(data)
                        Log.i("MSG", status.statusMessage ?: "Error occurred")
                    }
                }
                Activity.RESULT_CANCELED -> {
                    // The user canceled the operation.
                }
            }
            return
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>, grantResults: IntArray
    ) {
        when (requestCode) {
            REQUEST_CODE -> {
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                ) {
                    detectLocation()
                }
                return
            }
            else -> {
                // Ignore all other requests.
            }
        }
    }

    private fun detectLocation() {
        if (ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                LOCATION_PERMISSION_REQUEST_CODE
            )
            return
        }
        map.isMyLocationEnabled = true
        if (Settings.Secure.getInt(
                contentResolver,
                Settings.Secure.LOCATION_MODE
            ) != Settings.Secure.LOCATION_MODE_OFF
        ) {
            fusedLocationClient.lastLocation.addOnSuccessListener(this) { location ->
                // Got last known location. In some rare situations this can be null.
                if (location != null) {
                    val currentLatLng = LatLng(location.latitude, location.longitude)
                    getAddress(currentLatLng)
                    map.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 15f))
                }
            }
        } else {
            DialogBuilder.showDialog(this, "Please enable the location", object : DialogListener {
                override fun onPositiveClick(view: View) {
                    super.onPositiveClick(view)
                    val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                    startActivity(intent)
                }
            })
        }
    }


    private fun getAddress(latLng: LatLng) {
        val geocoder = Geocoder(this)
        val addresses: List<Address>?
        var address: Address? = null
        var addressText = ""
        var countryCode = ""
        var countryName = ""

        try {
            addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1)
            if (null != addresses && addresses.isNotEmpty()) {
                address = addresses[0]
                for (i in 0..address.maxAddressLineIndex) {
                    addressText += if (i == 0) address.getAddressLine(i) else "\n" + address.getAddressLine(
                        i
                    )
                }
                countryCode = address.countryCode
                countryName = address.countryName
            }
        } catch (e: IOException) {
            Log.e("MapsActivity", e.localizedMessage)
        }
        updateData(latLng, address?.locality, addressText, countryCode, countryName)
    }

    fun updateData(latLng: LatLng, locality: String?, address: String, countryCode : String, countryName : String) {
        mBinding.tvLocality.apply {
            visibility = View.VISIBLE
            text = locality
        }
        SharedPref.getInstance().setStringPreference(
            Constants.Location.NAME,
            locality ?: ""
        )
        mBinding.tvAddress.apply {
            visibility = View.VISIBLE
            text = address
        }
        SharedPref.getInstance().setStringPreference(
            Constants.Location.ADDRESS,
            address
        )
        SharedPref.getInstance().setDoublePreference(
            Constants.Location.LAT,
            latLng.latitude
        )
        SharedPref.getInstance().setDoublePreference(
            Constants.Location.LONG,
            latLng.longitude
        )
        SharedPref.getInstance().setStringPreference(
            Constants.Location.COUNTRY_CODE,
            countryCode
        )
        SharedPref.getInstance().setStringPreference(
            Constants.Location.COUNTRY_NAME,
            countryName
        )
    }
}
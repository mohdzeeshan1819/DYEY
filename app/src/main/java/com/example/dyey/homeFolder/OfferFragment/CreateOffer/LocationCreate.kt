package com.example.dyey.homeFolder.OfferFragment.CreateOffer


import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.example.dyey.apiInterfaces.AppInfo
import com.example.dyey.databinding.ActivityCreateOfferBinding
import com.example.dyey.databinding.ActivityLocationCreateBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import java.io.IOException
import java.util.*

class LocationCreate : AppCompatActivity() {

    private lateinit var binding: ActivityLocationCreateBinding
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLocationCreateBinding.inflate(layoutInflater)
        setContentView(binding.root)
        AppInfo.setContext(this)
        sharedPreferences = getSharedPreferences("LocationPreferences", Context.MODE_PRIVATE)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        binding.location.setOnClickListener {
            checkLocationPermission()
            binding.edittext.text=sharedPreferences.getString(KEY_LOCATION,"ropad")?.toString()
        }

        binding.SignIn.setOnClickListener { submitLocation() }
    }

    private fun checkLocationPermission() {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                PERMISSION_REQUEST_CODE
            )
        } else {
            // Permission has already been granted
            getLocation()
        }
    }

    private fun getLocation() {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {

            return
        }
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location: Location? ->
                location?.let {
                    saveLocation(it)
                }
            }
            .addOnFailureListener { e:Exception ->
                Log.e(TAG, "Error getting location: ${e.message}")
                Toast.makeText(this, "Error getting location", Toast.LENGTH_SHORT).show()
            }
    }

    private fun saveLocation(location: Location) {
        val geocoder = Geocoder(this, Locale.getDefault())
        try {
            val addresses: MutableList<Address>? = geocoder.getFromLocation(location.latitude, location.longitude, 1)
            val address: String? = addresses?.get(0)?.getAddressLine(0)
            address?.let {
                sharedPreferences.edit().putString(KEY_LOCATION, it).apply()
                Toast.makeText(this, "Location saved: $it", Toast.LENGTH_SHORT).show()
            } ?: run {
                Toast.makeText(this, "Address not found", Toast.LENGTH_SHORT).show()
            }
        } catch (e: IOException) {
            Log.e(TAG, "Error getting address: ${e.message}")
            Toast.makeText(this, "Error getting address", Toast.LENGTH_SHORT).show()
        }
    }


    private fun submitLocation() {
        val savedLocation = sharedPreferences.getString(KEY_LOCATION, "")
        // Do whatever you want to do with the saved location, like passing it to the next activity
        val intent = Intent(this, SearchByCuisin::class.java)
        intent.putExtra("LOCATION", savedLocation)
        startActivity(intent)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, proceed to get location
                getLocation()
            } else {
                // Permission denied, show a message or handle accordingly
                Toast.makeText(this, "Location permission denied", Toast.LENGTH_SHORT).show()
            }
        }
    }

    companion object {
        private const val TAG = "LocationCreate"
        private const val PERMISSION_REQUEST_CODE = 100
        private const val KEY_LOCATION = "location"
    }
}

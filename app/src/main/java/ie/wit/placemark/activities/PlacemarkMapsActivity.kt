package ie.wit.placemark.activities

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.ui.AppBarConfiguration
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import ie.wit.placemark.R
import ie.wit.placemark.databinding.ActivityPlacemarkMapsBinding
import ie.wit.placemark.databinding.ContentPlacemarkMapsBinding
import ie.wit.placemark.main.MainApp

class PlacemarkMapsActivity : AppCompatActivity() , GoogleMap.OnMarkerClickListener{

    private lateinit var binding: ActivityPlacemarkMapsBinding
    private lateinit var contentBinding: ContentPlacemarkMapsBinding
    lateinit var map: GoogleMap
    lateinit var app: MainApp



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        app = application as MainApp
        binding = ActivityPlacemarkMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        contentBinding = ContentPlacemarkMapsBinding.bind(binding.root)
        contentBinding.mapView.onCreate(savedInstanceState)
        contentBinding.mapView.getMapAsync{
            map = it
            configureMap()
        }
    }

    fun configureMap() {
        map.setOnMarkerClickListener(this)
        map.uiSettings.isZoomControlsEnabled = true //adds in the zoom

        app.placemarks.findAll().forEach {
            val loc = LatLng(it.lat, it.lng)
            val options = MarkerOptions().title(it.title).position(loc)
            map.addMarker(options)?.tag= it.id
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(loc, it.zoom))
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        contentBinding.mapView.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        contentBinding.mapView.onLowMemory()
    }

    override fun onPause() {
        super.onPause()
        contentBinding.mapView.onPause()
    }

    override fun onResume() {
        super.onResume()
        contentBinding.mapView.onResume()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        contentBinding.mapView.onSaveInstanceState(outState)
    }

    override fun onMarkerClick(marker: Marker): Boolean {
        val currentTitle: TextView = findViewById(R.id.currentTitle)
        val currentDescription: TextView = findViewById(R.id.currentDescription)
        currentTitle.text = marker.title // This updates the title
        //currentDescription.text = marker.description
        return false
    }
}
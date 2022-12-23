package ie.wit.placemark.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.ui.AppBarConfiguration
import com.google.android.gms.maps.GoogleMap
import ie.wit.placemark.databinding.ActivityPlacemarkMapsBinding
import ie.wit.placemark.databinding.ContentPlacemarkMapsBinding

class PlacemarkMapsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPlacemarkMapsBinding
    private lateinit var contentBinding: ContentPlacemarkMapsBinding
    lateinit var map: GoogleMap



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityPlacemarkMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        contentBinding = ContentPlacemarkMapsBinding.bind(binding.root)
        contentBinding.mapView.onCreate(savedInstanceState)
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

}
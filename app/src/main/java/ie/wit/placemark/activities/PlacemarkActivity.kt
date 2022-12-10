package ie.wit.placemark.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import ie.wit.placemark.databinding.ActivityPlacemarkBinding
import ie.wit.placemark.main.MainApp
import ie.wit.placemark.models.PlacemarkModel
import timber.log.Timber
import timber.log.Timber.i

class PlacemarkActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPlacemarkBinding
    var placemark = PlacemarkModel() //Creating placemark as a class member of placemark model
    lateinit var app: MainApp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPlacemarkBinding.inflate(layoutInflater)
        setContentView(binding.root)

        app = application as MainApp
        i("Placemark started")
        binding.btnAdd.setOnClickListener(){
            placemark.title = binding.placemarkTitle.text.toString() //Using placemark as a class member in the event handler
            placemark.description = binding.description.text.toString()
            if (placemark.title.isNotEmpty() && placemark.description.isNotEmpty()){
                app.placemarks.add(placemark.copy())
                i("add Button Pressed said: $placemark")
                for (i in app.placemarks.indices)
                {
                    i("Placemark[$i]:${this.app.placemarks[i]}")
                }
            }
            else {
                Snackbar
                    .make(it,"Please enter a title", Snackbar.LENGTH_LONG)
                    .show()
            }
        }
    }
}
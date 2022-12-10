package ie.wit.placemark.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import ie.wit.placemark.databinding.ActivityPlacemarkBinding
import ie.wit.placemark.models.PlacemarkModel
import timber.log.Timber
import timber.log.Timber.i

class PlacemarkActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPlacemarkBinding
    var placemark = PlacemarkModel() //Creating placemark as a class member of placemark model
    val placemarks = ArrayList<PlacemarkModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPlacemarkBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Timber.plant(Timber.DebugTree())
        i("Placemark Activity Started ...")

        binding.btnAdd.setOnClickListener(){
            placemark.title = binding.placemarkTitle.text.toString() //Using placemark as a class member in the event handler
            placemark.description = binding.description.text.toString()
            if (placemark.title.isNotEmpty() && placemark.description.isNotEmpty()){
                placemarks.add(placemark.copy())
                i("add Button Pressed said: $placemark")
                for (i in placemarks.indices)
                {
                    i("Placemark[$i]:${this.placemarks[i]}")
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
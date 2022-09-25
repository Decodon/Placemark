package ie.wit.placemark

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import ie.wit.placemark.databinding.ActivityPlacemarkBinding
import timber.log.Timber
import timber.log.Timber.i

class PlacemarkActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPlacemarkBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPlacemarkBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Timber.plant(Timber.DebugTree())

        i("Placemark Activity Started ...")

        binding.btnAdd.setOnClickListener() {
            val placemarkTitle = binding.placemarkTitle.text.toString()
            if (placemarkTitle.isNotEmpty()) {
                i("add Button Pressed: $placemarkTitle")
            } else {
                Snackbar
                    .make(it, "Please enter a title", Snackbar.LENGTH_LONG)
                    .show()
            }
        }
    }
}
package ie.wit.placemark.views.placemark

import android.content.Intent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.squareup.picasso.MemoryPolicy
import com.squareup.picasso.NetworkPolicy
import com.squareup.picasso.Picasso
import ie.wit.placemark.R
import ie.wit.placemark.views.editlocation.EditLocationView
import ie.wit.placemark.databinding.ActivityPlacemarkBinding
import ie.wit.placemark.helpers.showImagePicker
import ie.wit.placemark.main.MainApp
import ie.wit.placemark.models.Location
import ie.wit.placemark.models.PlacemarkModel

import timber.log.Timber

class PlacemarkPresenter(private val view: PlacemarkView) {

    var placemark = PlacemarkModel()
    var app: MainApp = view.application as MainApp
    var binding: ActivityPlacemarkBinding = ActivityPlacemarkBinding.inflate(view.layoutInflater)
    private lateinit var imageIntentLauncher : ActivityResultLauncher<Intent>
    private lateinit var mapIntentLauncher : ActivityResultLauncher<Intent>
    var edit = false;
    private val location = Location(52.245696, -7.139102, 15f)
    var map: GoogleMap? = null
    //location service


    init {
        if (view.intent.hasExtra("placemark_edit")) {
            edit = true
            placemark = view.intent.extras?.getParcelable("placemark_edit")!!
            view.showPlacemark(placemark)
        }
        else {
            placemark.lat = location.lat
            placemark.lng = location.lng
        }
        registerImagePickerCallback()
        registerMapCallback()
    }

    fun doAddOrSave(title: String, description: String) {
        placemark.title = title
        placemark.description = description
        if (edit) {
            app.placemarks.update(placemark)
        } else {
            app.placemarks.create(placemark)
        }

        view.finish()

    }

    fun doCancel() {
        view.finish()

    }

    fun doDelete() {
        app.placemarks.delete(placemark)
        view.finish()

    }

    fun doSelectImage() {
        showImagePicker(imageIntentLauncher)
    }

    fun doSetLocation() {

        if (placemark.zoom != 0f) {
            location.lat =  placemark.lat
            location.lng = placemark.lng
            location.zoom = placemark.zoom
            locationUpdate(placemark.lat, placemark.lng)
        }
        val launcherIntent = Intent(view, EditLocationView::class.java)
            .putExtra("location", location)
        mapIntentLauncher.launch(launcherIntent)
    }

    fun cachePlacemark (title: String, description: String) {
        placemark.title = title
        placemark.description = description
    }

    private fun registerImagePickerCallback() {

        imageIntentLauncher =
            view.registerForActivityResult(ActivityResultContracts.StartActivityForResult())
            { result ->
                when(result.resultCode){
                    AppCompatActivity.RESULT_OK -> {
                        if (result.data != null) {
                            Timber.i("Got Result ${result.data!!.data}")
                            placemark.image = result.data!!.data!!
                            Picasso.get()
                                .load(placemark.image)
                                .networkPolicy(NetworkPolicy.NO_CACHE)
                                .memoryPolicy(MemoryPolicy.NO_CACHE)
                                .into(binding.placemarkImage)
                            binding.chooseImage.setText(R.string.change_placemark_image)
                        }
                    }
                    AppCompatActivity.RESULT_CANCELED -> { } else -> { }
                }
            }
    }

    private fun registerMapCallback() {
        mapIntentLauncher =
            view.registerForActivityResult(ActivityResultContracts.StartActivityForResult())
            { result ->
                when (result.resultCode) {
                    AppCompatActivity.RESULT_OK -> {
                        if (result.data != null) {
                            Timber.i("Got Location ${result.data.toString()}")
                            val location = result.data!!.extras?.getParcelable<Location>("location")!!
                            Timber.i("Location == $location")
                            placemark.lat = location.lat
                            placemark.lng = location.lng
                            placemark.zoom = location.zoom
                        } // end of if
                    }
                    AppCompatActivity.RESULT_CANCELED -> { } else -> { }
                }

            }
    }

    fun doConfigureMap(m: GoogleMap) {
        map = m
        locationUpdate(placemark.lat, placemark.lng)
    }

    fun locationUpdate(lat: Double, lng: Double) {
        placemark.lat = lat
        placemark.lng = lng
        placemark.zoom = 15f
        map?.clear()
        map?.uiSettings?.setZoomControlsEnabled(true)
        val options = MarkerOptions().title(placemark.title).position(LatLng(placemark.lat, placemark.lng))
        map?.addMarker(options)
        map?.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(placemark.lat, placemark.lng), placemark.zoom))
        view?.showPlacemark(placemark)
    }
}
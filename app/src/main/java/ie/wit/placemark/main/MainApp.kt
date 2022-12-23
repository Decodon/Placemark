package ie.wit.placemark.main

import android.app.Application
import ie.wit.placemark.models.PlacemarkJSONStore
import ie.wit.placemark.models.PlacemarkMemStore
import ie.wit.placemark.models.PlacemarkModel
import ie.wit.placemark.models.PlacemarkStore
import timber.log.Timber
import timber.log.Timber.i

class MainApp : Application() {

    //val placemarks = ArrayList<PlacemarkModel>()
    //val placemarks = PlacemarkMemStore()
    lateinit var placemarks: PlacemarkStore

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        //placemarks = PlacemarkMemStore()
        placemarks = PlacemarkJSONStore(applicationContext)
        i("Placemark started")
    }
}
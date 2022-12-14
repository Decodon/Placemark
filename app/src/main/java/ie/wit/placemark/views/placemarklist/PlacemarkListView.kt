package ie.wit.placemark.views.placemarklist

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import ie.wit.placemark.R
import ie.wit.placemark.adapters.PlacemarkAdapter
import ie.wit.placemark.adapters.PlacemarkListener
import ie.wit.placemark.databinding.ActivityPlacemarkListBinding
import ie.wit.placemark.main.MainApp
import ie.wit.placemark.models.PlacemarkModel
import timber.log.Timber.i

class PlacemarkListView : AppCompatActivity(), PlacemarkListener {

    lateinit var app: MainApp
    private lateinit var binding: ActivityPlacemarkListBinding
    lateinit var presenter: PlacemarkListPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPlacemarkListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.toolbar.title = title
        setSupportActionBar(binding.toolbar)
        presenter = PlacemarkListPresenter(this)
        app = application as MainApp

        val layoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = layoutManager
        loadPlacemarks()

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_add -> { presenter.doAddPlacemark() }
            R.id.item_map -> { presenter.doShowPlacemarksMap() }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onPlacemarkClick(placemark: PlacemarkModel) {
        presenter.doEditPlacemark(placemark)

    }

    private fun loadPlacemarks() {
        binding.recyclerView.adapter = PlacemarkAdapter(presenter.getPlacemarks(), this)
        binding.recyclerView.adapter?.notifyDataSetChanged()
    }

    override fun onResume() {
        //update the view
        binding.recyclerView.adapter?.notifyDataSetChanged()
        i("recyclerView onResume")
        super.onResume()
    }
}
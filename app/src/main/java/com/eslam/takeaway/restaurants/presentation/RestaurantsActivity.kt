package com.eslam.takeaway.restaurants.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.util.Consumer
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.eslam.takeaway.R
import com.eslam.takeaway.restaurants.model.RestaurantUIModel
import kotlinx.android.synthetic.main.activity_restaurants.*

/**
 * The main activity showing the list of restaurants
 */
class RestaurantsActivity : AppCompatActivity() {

    private val viewModel by lazy {
        ViewModelProviders.of(this, RestaurantViewModelFactory()).get(RestaurantsViewModel::class.java)
    }

    /**
     * Init views and observe on view model data changes
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_restaurants)
        initSearchView()
        initSpinner()
        initRecyclerView()
        viewModel.restaurants.observe(this, Observer { updateList(it) })
    }

    /**
     * Initialize the recycler view of restaurants
     */
    private fun initRecyclerView() {
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.addItemDecoration(DividerItemDecoration(this, LinearLayoutManager.VERTICAL))
    }

    /**
     * Initialize search view with the Text Listener
     */
    private fun initSearchView() {
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                viewModel.onSearchUpdated(query ?: "")
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                viewModel.onSearchUpdated(newText ?: "")
                return true
            }

        })
    }

    /**
     * Initialize the spinner and sets its listener
     */
    private fun initSpinner() {
        val list = resources.getStringArray(R.array.sorting_keys).toList()
        sortSpinner.attachDataSource(list)
        sortSpinner.selectedIndex = 0
        sortSpinner.setOnSpinnerItemSelectedListener { _, _, position, _ ->
            viewModel.onSpinnerItemSelected(list[position])
        }
    }

    /**
     * Notifies the adapter with the changed list of restaurants
     */
    private fun updateList(restaurant: List<RestaurantUIModel>) {
        recyclerView.adapter = RestaurantAdapter(
            restaurant,
            Consumer { viewModel.onFavoriteClicked(it) }
        )
    }
}

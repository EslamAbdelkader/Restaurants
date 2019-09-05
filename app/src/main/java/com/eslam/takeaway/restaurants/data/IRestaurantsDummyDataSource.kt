package com.eslam.takeaway.restaurants.data

import android.content.Context
import com.eslam.takeaway.restaurants.model.Restaurant
import com.eslam.takeaway.restaurants.repository.IRestaurantsDataSource
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import io.reactivex.Observable
import java.io.InputStreamReader
import javax.inject.Inject

private const val RESTAURANTS_ASSET_FILE = "restaurants.json"

/**
 * Dummy implementation for restaurants data source, simply reading the restaurants list from an asset file
 */
class IRestaurantsDummyDataSource @Inject constructor() : IRestaurantsDataSource {

    /**
     * Application context to access assets
     */
    @Inject
    lateinit var context: Context

    /**
     * Fetch all restaurants from the asset file
     */
    override fun fetchRestaurants(): Observable<List<Restaurant>> {
        val assetManager = context.assets
        val inputStream = assetManager.open(RESTAURANTS_ASSET_FILE)
        val reader = InputStreamReader(inputStream)
        val listType = object : TypeToken<List<Restaurant>>() { }.type
        val restaurants: List<Restaurant> = Gson().fromJson(reader, listType)
        return Observable.just(restaurants)
    }
}
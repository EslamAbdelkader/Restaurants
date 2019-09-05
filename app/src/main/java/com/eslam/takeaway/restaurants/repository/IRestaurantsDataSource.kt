package com.eslam.takeaway.restaurants.repository

import com.eslam.takeaway.restaurants.model.Restaurant
import io.reactivex.Observable

/**
 * Data source of restaurants for the CRUD operations over the restaurants
 */
interface IRestaurantsDataSource {

    /**
     * Fetch all restaurants
     */
    fun fetchRestaurants() : Observable<List<Restaurant>>
}
package com.eslam.takeaway.restaurants.presentation

import com.eslam.takeaway.restaurants.model.Restaurant
import io.reactivex.Observable

interface IRestaurantInteractor {

    /**
     * Fetches all restaurants, with isFavorite information included
     */
    fun fetchAllRestaurants() : Observable<List<Restaurant>>

    /**
     * marks a restaurant as a isFavorite
     */
    fun favorite(restaurant: String)

    /**
     * marks a restaurant as unfavorite
     */
    fun unfavorite(restaurant: String)

}
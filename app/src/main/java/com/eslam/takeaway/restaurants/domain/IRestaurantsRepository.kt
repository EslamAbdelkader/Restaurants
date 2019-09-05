package com.eslam.takeaway.restaurants.domain

import com.eslam.takeaway.restaurants.model.Restaurant
import io.reactivex.Observable

/**
 * Interface for restaurants repository, responsible for the CRUD operations on restaurants,
 * as well as synchronizing between different data sources (Not currently needed or implemented)
 * Currently only fetchAll is needed.
 */
interface IRestaurantsRepository {

    /**
     * Retrieves an Observable of a list of restaurants
     */
    fun fetchAllRestaurants() : Observable<List<Restaurant>>
}

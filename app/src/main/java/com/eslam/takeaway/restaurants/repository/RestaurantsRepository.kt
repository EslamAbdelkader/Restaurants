package com.eslam.takeaway.restaurants.repository

import com.eslam.takeaway.restaurants.domain.IRestaurantsRepository
import com.eslam.takeaway.restaurants.model.Restaurant
import io.reactivex.Observable
import javax.inject.Inject

/**
 * Concrete implementation of Restaurants Repository for handling CRUD operations over the restaurants
 */
class RestaurantsRepository @Inject constructor() : IRestaurantsRepository {

    /**
     * The only data source available for retrieving restaurants
     */
    @Inject
    lateinit var dataSource: IRestaurantsDataSource

    /**
     * Fetch all restaurants
     */
    override fun fetchAllRestaurants(): Observable<List<Restaurant>> {
        return dataSource.fetchRestaurants()
    }
}
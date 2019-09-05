package com.eslam.takeaway.restaurants.domain

import io.reactivex.Observable

/**
 * Interface for favorite repository, responsible for the CRUD operations on
 * favorite restaurants (represented by their names)
 */
interface IFavoritesRepository {

    /**
     * Retrieves an observable of a set of all favorites, which gets updated whenever
     * a favorite is inserted or deleted
     */
    fun fetchAllFavorites(): Observable<Set<String>>

    /**
     * Insert a restaurant's name in the list of favorites
     */
    fun insertFavorite(restaurantName: String)

    /**
     * Deletes a restaurant's name from the list of favorites
     */
    fun deleteFavorite(restaurantName: String)
}

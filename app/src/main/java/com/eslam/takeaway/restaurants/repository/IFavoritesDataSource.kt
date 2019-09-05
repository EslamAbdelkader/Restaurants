package com.eslam.takeaway.restaurants.repository

/**
 * Data source of favorites for the CRUD operations over the favorites
 */
interface IFavoritesDataSource {

    /**
     * Fetch all favorites
     */
    fun fetchFavorites() : Set<String>

    /**
     * Insert a favorite restaurant
     */
    fun insertFavorite(restaurantName: String) : Boolean

    /**
     * Delete a restaurant from favorites
     */
    fun deleteFavorite(restaurantName: String) : Boolean
}
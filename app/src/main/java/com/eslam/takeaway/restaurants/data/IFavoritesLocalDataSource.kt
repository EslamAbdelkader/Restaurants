package com.eslam.takeaway.restaurants.data

import android.content.Context
import com.eslam.takeaway.restaurants.repository.IFavoritesDataSource
import javax.inject.Inject

private const val SHARED_PREFERENCES_NAME = "restaurants_shared_pref"

private const val FAVORITES_KEY = "favorites"

/**
 * Implementation of Local data source for storing and retrieving isFavorite
 * restaurants, represented by their names
 */
class IFavoritesLocalDataSource @Inject constructor() : IFavoritesDataSource {

    /**
     * Application context for accessing the shared preference (Our local storage)
     */
    @Inject
    lateinit var context: Context

    /**
     * Fetches all favorites as a Set of restaurant names
     */
    override fun fetchFavorites() : Set<String> {
        val sharedPref = context.getSharedPreferences(SHARED_PREFERENCES_NAME,Context.MODE_PRIVATE)
        return sharedPref.getStringSet(FAVORITES_KEY, setOf()) as Set<String>
    }

    /**
     * Adding a new restaurant to our favorites
     */
    override fun insertFavorite(restaurantName: String): Boolean {
        val favorites = fetchFavorites().toMutableSet()
        favorites.add(restaurantName)
        val sharedPref = context.getSharedPreferences(SHARED_PREFERENCES_NAME,Context.MODE_PRIVATE)
        return sharedPref.edit().putStringSet(FAVORITES_KEY, favorites).commit()
    }

    /**
     * Removing a restaurant from our list of favorites
     */
    override fun deleteFavorite(restaurantName: String): Boolean {
        val favorites = fetchFavorites().toMutableSet()
        favorites.remove(restaurantName)
        val sharedPref = context.getSharedPreferences(SHARED_PREFERENCES_NAME,Context.MODE_PRIVATE)
        return sharedPref.edit().putStringSet(FAVORITES_KEY, favorites).commit()
    }
}
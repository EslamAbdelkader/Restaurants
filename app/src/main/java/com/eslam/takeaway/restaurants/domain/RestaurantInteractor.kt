package com.eslam.takeaway.restaurants.domain

import com.eslam.takeaway.restaurants.model.Restaurant
import com.eslam.takeaway.restaurants.presentation.IRestaurantInteractor
import io.reactivex.Observable
import io.reactivex.functions.BiFunction
import javax.inject.Inject

/**
 * Restaurants Interactor responsible for retrieving restaurants and doing any business logic
 * required on them before passing them to the view model.
 * In our case, it marks restaurants as favorite or not, based on the favorites retrieved
 * from [IFavoritesRepository]
 */
class RestaurantInteractor @Inject constructor() : IRestaurantInteractor {

    /**
     * Restaurants Repository for retrieving restaurants
     */
    @Inject
    lateinit var restaurantRepo: IRestaurantsRepository

    /**
     * Restaurants Repository for retrieving favorites list
     */
    @Inject
    lateinit var favoritesRepo: IFavoritesRepository

    /**
     * Fetches all restaurants and pass them to the view mode, after marking them as favorite
     * or not, based on the favorites retrieved from [favoritesRepo]
     */
    override fun fetchAllRestaurants(): Observable<List<Restaurant>> {
        val favoritesObservable = favoritesRepo.fetchAllFavorites()
        val restaurantsObservable = restaurantRepo.fetchAllRestaurants()

        return Observable.combineLatest(favoritesObservable, restaurantsObservable, BiFunction { favorites, restaurants ->
            restaurants.forEach {
                it.isFavorite = favorites.contains(it.name)
            }
            return@BiFunction restaurants
        })
    }

    /**
     * Mark a [restaurant] as favorite
     */
    override fun favorite(restaurant: String) {
        favoritesRepo.insertFavorite(restaurant)
    }

    /**
     * Unmark a [restaurant] as a favorite
     */
    override fun unfavorite(restaurant: String) {
        favoritesRepo.deleteFavorite(restaurant)
    }

}

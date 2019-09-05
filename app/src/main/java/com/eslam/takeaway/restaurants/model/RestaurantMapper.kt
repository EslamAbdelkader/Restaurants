package com.eslam.takeaway.restaurants.model

import java.util.function.BiFunction
import javax.inject.Inject

/**
 * A mapper class responsible for mapping [Restaurant] into [RestaurantUIModel] using the sortingKey
 */
open class RestaurantMapper @Inject constructor() : BiFunction<Restaurant, String, RestaurantUIModel> {

    /**
     * mapping [restaurant] of type [Restaurant] to [RestaurantUIModel]
     */
    override fun apply(restaurant: Restaurant, sortingKey: String): RestaurantUIModel {
        return RestaurantUIModel(
            name = restaurant.name,
            status = restaurant.status.value,
            sortValue = restaurant.sortingValues[sortingKey] ?: 0.0,
            isFavorite = restaurant.isFavorite
        )
    }
}
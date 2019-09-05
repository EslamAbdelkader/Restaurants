package com.eslam.takeaway.restaurants.model

/**
 * The UI model representing how the restaurant is visualized.
 * This model is passed between view/presentation layers
 */
data class RestaurantUIModel (
    val name: String = "",
    val status: String = Status.OPEN.value,
    val sortValue: Double = 0.0,
    val isFavorite: Boolean = false
)
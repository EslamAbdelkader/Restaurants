package com.eslam.takeaway.restaurants.model

import com.google.gson.annotations.SerializedName

/**
 * The restaurant model retrieved from the data source and passed between domain layers
 */
data class Restaurant (
    val name : String = "",
    val status: Status = Status.OPEN,
    var isFavorite: Boolean = false,
    val sortingValues: Map<String, Double> = mapOf()
)

/**
 * An enum representing the status of the restaurant
 */
enum class Status(val value: String) {
    @SerializedName("open") OPEN("open"),
    @SerializedName("order ahead") ORDER_AHEAD("order ahead"),
    @SerializedName("closed") CLOSED("closed")
}

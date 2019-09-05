package com.eslam.takeaway.restaurants.presentation

import com.eslam.takeaway.restaurants.model.RestaurantUIModel
import com.google.common.truth.Truth.assertThat
import org.junit.Before
import org.junit.Test

class RestaurantDiffCallbackTest {

    private lateinit var oldList : List<RestaurantUIModel>

    @Before
    fun setUp() {
        oldList = mutableListOf(
            RestaurantUIModel(name = "res1", isFavorite = true)
        )
    }

    @Test
    fun `same item is decided based on the name`(){
        // Given
        val newList = mutableListOf(RestaurantUIModel(name = "res1", isFavorite = false))
        val diffCallback = RestaurantDiffCallback(oldList,newList)

        // When
        val result = diffCallback.areItemsTheSame(0, 0)

        // Then
        assertThat(result).isTrue()
    }

    @Test
    fun `same content is decided based on the whole data`(){
        // Given
        var newList = mutableListOf(RestaurantUIModel(name = "res1", isFavorite = false))
        var diffCallback = RestaurantDiffCallback(oldList,newList)

        // When
        var result = diffCallback.areContentsTheSame(0, 0)

        // Then
        assertThat(result).isFalse()

        // Given
        newList = mutableListOf(RestaurantUIModel(name = "res1", isFavorite = true))
        diffCallback = RestaurantDiffCallback(oldList,newList)

        // When
        result = diffCallback.areContentsTheSame(0, 0)

        // Then
        assertThat(result).isTrue()
    }
}
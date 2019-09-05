package com.eslam.takeaway.restaurants.model

import com.google.common.truth.Truth.assertThat
import org.junit.Test

class RestaurantMapperTest {
    private val mapper = RestaurantMapper()

    @Test
    fun `Test mapper`(){
        // Given
        val restaurant = Restaurant("restaurant",Status.OPEN,true,mapOf("distance" to 1.0))

        // When
        val uiModel = mapper.apply(restaurant, "distance")

        // Then
        assertThat(uiModel).isNotNull()
        assertThat(uiModel.name).isEqualTo("restaurant")
        assertThat(uiModel.status).isEqualTo("open")
        assertThat(uiModel.sortValue).isEqualTo(1.0)
        assertThat(uiModel.isFavorite).isEqualTo(true)
    }
}
package com.eslam.takeaway.restaurants.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import com.eslam.takeaway.restaurants.model.Restaurant
import com.eslam.takeaway.restaurants.model.RestaurantUIModel
import com.eslam.takeaway.restaurants.model.Status
import com.google.common.truth.Truth.assertThat
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Observable
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.util.function.BiFunction

class RestaurantsViewModelTest {

    /**
     * For LiveData Instant Execution
     */
    @get:Rule
    var rule = InstantTaskExecutorRule()

    private lateinit var viewModel: RestaurantsViewModel

    private lateinit var interactor: IRestaurantInteractor

    private lateinit var mapper: BiFunction<Restaurant, String, RestaurantUIModel>

    private lateinit var restaurantsLiveData: LiveData<List<RestaurantUIModel>>

    @Before
    fun setUp() {
        viewModel = RestaurantsViewModel()
        interactor = mock()
        mapper = mock()

        viewModel.interactor = interactor
        viewModel.mapper = mapper
        restaurantsLiveData = viewModel.restaurants

        // Given for all test cases
        whenever(mapper.apply(any(), any())).then {
            val restaurant = it.arguments[0] as Restaurant
            val sortingKey = it.arguments[1] as String
            return@then RestaurantUIModel(
                name = restaurant.name,
                status = restaurant.status.value,
                sortValue = restaurant.sortingValues[sortingKey] ?: 0.0,
                isFavorite = restaurant.isFavorite
            )
        }
    }

    @Test
    fun `Restaurants are sorted by favorite`() {
        // Given
        whenever(interactor.fetchAllRestaurants()).thenReturn(
            Observable.just(
                listOf(
                    Restaurant(name = "res1", isFavorite = false),
                    Restaurant(name = "res2", isFavorite = true),
                    Restaurant(name = "res3", isFavorite = false)
                )
            )
        )

        // When
        viewModel.initData()

        // Then
        assertThat(restaurantsLiveData.value).containsExactly(
            RestaurantUIModel(name = "res2", isFavorite = true),
            RestaurantUIModel(name = "res1", isFavorite = false),
            RestaurantUIModel(name = "res3", isFavorite = false)
        ).inOrder()
    }

    @Test
    fun `Restaurants are sorted by status`() {
        // Given
        whenever(interactor.fetchAllRestaurants()).thenReturn(
            Observable.just(
                listOf(
                    Restaurant(name = "res1", status = Status.ORDER_AHEAD),
                    Restaurant(name = "res2", status = Status.CLOSED),
                    Restaurant(name = "res3", status = Status.OPEN)
                )
            )
        )

        // When
        viewModel.initData()

        // Then
        assertThat(restaurantsLiveData.value).containsExactly(
            RestaurantUIModel(name = "res3", status = Status.OPEN.value),
            RestaurantUIModel(name = "res1", status = Status.ORDER_AHEAD.value),
            RestaurantUIModel(name = "res2", status = Status.CLOSED.value)
        ).inOrder()
    }


    @Test
    fun `Restaurants are sorted by sorting key`() {
        // Given
        whenever(interactor.fetchAllRestaurants()).thenReturn(
            Observable.just(
                listOf(
                    Restaurant(name = "res1", sortingValues = mapOf("distance" to 5.0)),
                    Restaurant(name = "res2", sortingValues = mapOf("distance" to 1.0)),
                    Restaurant(name = "res3", sortingValues = mapOf("distance" to 10.0))
                )
            )
        )

        // When
        viewModel.initData()
        viewModel.onSpinnerItemSelected("distance")

        // Then
        assertThat(restaurantsLiveData.value).containsExactly(
            RestaurantUIModel(name = "res3", sortValue = 10.0),
            RestaurantUIModel(name = "res1", sortValue = 5.0),
            RestaurantUIModel(name = "res2", sortValue = 1.0)
        ).inOrder()
    }

    @Test
    fun `Restaurants are sorted by bestMatch by default`() {
        // Given
        whenever(interactor.fetchAllRestaurants()).thenReturn(
            Observable.just(
                listOf(
                    Restaurant(name = "res1", sortingValues = mapOf("bestMatch" to 5.0)),
                    Restaurant(name = "res2", sortingValues = mapOf("bestMatch" to 1.0)),
                    Restaurant(name = "res3", sortingValues = mapOf("bestMatch" to 10.0))
                )
            )
        )

        // When
        viewModel.initData()

        // Then
        assertThat(restaurantsLiveData.value).containsExactly(
            RestaurantUIModel(name = "res3", sortValue = 10.0),
            RestaurantUIModel(name = "res1", sortValue = 5.0),
            RestaurantUIModel(name = "res2", sortValue = 1.0)
        ).inOrder()
    }

    @Test
    fun `Sorting by favorite has higher priority than sorting by status`() {
        // Given
        whenever(interactor.fetchAllRestaurants()).thenReturn(
            Observable.just(
                listOf(
                    Restaurant(name = "res1", isFavorite = false, status = Status.CLOSED),
                    Restaurant(name = "res2", isFavorite = true, status = Status.CLOSED),
                    Restaurant(name = "res3", isFavorite = false, status = Status.OPEN),
                    Restaurant(name = "res4", isFavorite = true, status = Status.OPEN)
                )
            )
        )

        // When
        viewModel.initData()

        // Then
        assertThat(restaurantsLiveData.value).containsExactly(
            RestaurantUIModel(name = "res4", isFavorite = true, status = Status.OPEN.value),
            RestaurantUIModel(name = "res2", isFavorite = true, status = Status.CLOSED.value),
            RestaurantUIModel(name = "res3", isFavorite = false, status = Status.OPEN.value),
            RestaurantUIModel(name = "res1", isFavorite = false, status = Status.CLOSED.value)
        ).inOrder()
    }

    @Test
    fun `Sorting by status has higher priority than sorting by sorting key`() {
        // Given
        whenever(interactor.fetchAllRestaurants()).thenReturn(
            Observable.just(
                listOf(
                    Restaurant(name = "res1", status = Status.CLOSED, sortingValues = mapOf("bestMatch" to 1.0)),
                    Restaurant(name = "res2", status = Status.OPEN, sortingValues = mapOf("bestMatch" to 1.0)),
                    Restaurant(name = "res3", status = Status.CLOSED, sortingValues = mapOf("bestMatch" to 5.0)),
                    Restaurant(name = "res4", status = Status.OPEN, sortingValues = mapOf("bestMatch" to 5.0))
                )
            )
        )

        // When
        viewModel.initData()

        // Then
        assertThat(restaurantsLiveData.value).containsExactly(
            RestaurantUIModel(name = "res4", status = Status.OPEN.value, sortValue = 5.0),
            RestaurantUIModel(name = "res2", status = Status.OPEN.value, sortValue = 1.0),
            RestaurantUIModel(name = "res3", status = Status.CLOSED.value, sortValue = 5.0),
            RestaurantUIModel(name = "res1", status = Status.CLOSED.value, sortValue = 1.0)
        ).inOrder()
    }
}
package com.eslam.takeaway.restaurants.domain

import com.eslam.takeaway.restaurants.model.Restaurant
import com.google.common.truth.Truth.assertThat
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers.anyString

class RestaurantInteractorTest {

    private lateinit var interactor: RestaurantInteractor

    private lateinit var restaurantRepo: IRestaurantsRepository

    private lateinit var favoritesRepo: IFavoritesRepository

    @Before
    fun setUp() {
        interactor = RestaurantInteractor()
        restaurantRepo = mock()
        favoritesRepo = mock()

        interactor.restaurantRepo = restaurantRepo
        interactor.favoritesRepo = favoritesRepo

        // Given for all test cases
        val restaurants = listOf(Restaurant("res1"), Restaurant("res2"), Restaurant("res3"))
        whenever(restaurantRepo.fetchAllRestaurants()).thenReturn(Observable.just(restaurants))

    }

    @Test
    fun `Interactor combines data from both repos and marks which restaurants are favored`() {
        // Given
         val favorites = setOf("res1", "res3")
        whenever(favoritesRepo.fetchAllFavorites()).thenReturn(Observable.just(favorites))

        // When
        val testObserver = interactor.fetchAllRestaurants().test()

        // Then
        assertThat(testObserver.values().size).isEqualTo(1)
        assertThat(testObserver.values().first()).containsExactly(
            Restaurant(name = "res1",isFavorite = true),
            Restaurant(name = "res2",isFavorite = false),
            Restaurant(name = "res3",isFavorite = true)
        ).inOrder()
    }

    @Test
    fun `Interactor emits modified data when a restaurant is marked as favorite`() {
        // Given
        val favorites = mutableSetOf("res1", "res3")
        val favoritesSubject: BehaviorSubject<Set<String>> = BehaviorSubject.createDefault(favorites)
        whenever(favoritesRepo.fetchAllFavorites()).thenReturn(favoritesSubject)
        whenever(favoritesRepo.insertFavorite(anyString())).then {
            favorites.add(it.arguments[0] as String)
            favoritesSubject.onNext(favorites)
        }

        // When
        val testObserver = interactor.fetchAllRestaurants().test()

        // Then
        assertThat(testObserver.values().size).isEqualTo(1)
        assertThat(testObserver.values().first()).contains(
            Restaurant(name = "res2", isFavorite = false)
        )

        // When
        interactor.favorite("res2")

        // Then
        assertThat(testObserver.values().size).isEqualTo(2)
        assertThat(testObserver.values().first()).contains(
            Restaurant(name = "res2", isFavorite = true)
        )
    }

    @Test
    fun `Interactor emits modified data when a restaurant is unmarked as favorite`() {
        // Given
        val favorites = mutableSetOf("res1", "res2", "res3")
        val favoritesSubject: BehaviorSubject<Set<String>> = BehaviorSubject.createDefault(favorites)
        whenever(favoritesRepo.fetchAllFavorites()).thenReturn(favoritesSubject)
        whenever(favoritesRepo.deleteFavorite(anyString())).then {
            favorites.remove(it.arguments[0] as String)
            favoritesSubject.onNext(favorites)
        }

        // When
        val testObserver = interactor.fetchAllRestaurants().test()

        // Then
        assertThat(testObserver.values().size).isEqualTo(1)
        assertThat(testObserver.values().first()).contains(
            Restaurant(name = "res2", isFavorite = true)
        )

        // When
        interactor.unfavorite("res2")

        // Then
        assertThat(testObserver.values().size).isEqualTo(2)
        assertThat(testObserver.values().first()).contains(
            Restaurant(name = "res2", isFavorite = false)
        )
    }

    @Test
    fun `Interactor forwards favoring to dataSource`() {
        // When
        interactor.favorite("xyz")

        // Then
        verify(favoritesRepo, times(1)).insertFavorite("xyz")
    }

    @Test
    fun `Interactor forwards unfavoring to dataSource`() {
        // When
        interactor.unfavorite("xyz")

        // Then
        verify(favoritesRepo, times(1)).deleteFavorite("xyz")
    }


}
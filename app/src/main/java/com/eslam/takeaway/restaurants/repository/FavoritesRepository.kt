package com.eslam.takeaway.restaurants.repository

import com.eslam.takeaway.restaurants.domain.IFavoritesRepository
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import javax.inject.Inject

/**
 * Concrete implementation of favorites repository for handling CRUD operations (locally) over favorites
 * It's also responsible of keeping the [favorites] Observable updated, by notifying it with
 * any favorite inserted or deleted
 */
class FavoritesRepository @Inject constructor() : IFavoritesRepository {

    /**
     * The only available (local) data source for retrieving and storing favorites
     */
    @Inject
    lateinit var dataSource: IFavoritesDataSource

    /**
     * Represents a live observable of favorites
     */
    private val favorites by lazy { BehaviorSubject.createDefault(fetchFavorites()) }

    /**
     * Fetch all favorites
     */
    override fun fetchAllFavorites(): Observable<Set<String>> {
        return favorites
    }

    /**
     * Inserts a new favorite and notifies the live observable
     */
    override fun insertFavorite(restaurantName: String) {
        dataSource.insertFavorite(restaurantName)
        notifyFavoritesSubject()
    }

    /**
     * Deletes a favorite and notifies the live observable
     */
    override fun deleteFavorite(restaurantName: String) {
        dataSource.deleteFavorite(restaurantName)
        notifyFavoritesSubject()
    }

    /**
     * Fetching favorites from the local data source
     */
    private fun fetchFavorites(): Set<String> {
        return dataSource.fetchFavorites()
    }

    /**
     * Notifying the live observable with changes
     */
    private fun notifyFavoritesSubject() {
        favorites.onNext(fetchFavorites())
    }
}
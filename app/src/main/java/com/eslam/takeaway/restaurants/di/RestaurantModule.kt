package com.eslam.takeaway.restaurants.di

import com.eslam.takeaway.di.PerActivity
import com.eslam.takeaway.restaurants.data.IFavoritesLocalDataSource
import com.eslam.takeaway.restaurants.data.IRestaurantsDummyDataSource
import com.eslam.takeaway.restaurants.domain.IFavoritesRepository
import com.eslam.takeaway.restaurants.domain.IRestaurantsRepository
import com.eslam.takeaway.restaurants.domain.RestaurantInteractor
import com.eslam.takeaway.restaurants.model.Restaurant
import com.eslam.takeaway.restaurants.model.RestaurantMapper
import com.eslam.takeaway.restaurants.model.RestaurantUIModel
import com.eslam.takeaway.restaurants.presentation.IRestaurantInteractor
import com.eslam.takeaway.restaurants.repository.IFavoritesDataSource
import com.eslam.takeaway.restaurants.repository.FavoritesRepository
import com.eslam.takeaway.restaurants.repository.IRestaurantsDataSource
import com.eslam.takeaway.restaurants.repository.RestaurantsRepository
import dagger.Binds
import dagger.Module
import java.util.function.BiFunction

/**
 * A module with the lifetime of the restaurants screen scope
 */
@Module
abstract class RestaurantModule {

    @Binds
    @PerActivity
    abstract fun provideMapper(mapper: RestaurantMapper) : BiFunction<Restaurant, String, RestaurantUIModel>

    @Binds
    @PerActivity
    abstract fun provideInteractor(interactor: RestaurantInteractor) : IRestaurantInteractor

    @Binds
    @PerActivity
    abstract fun provideRestaurantDataSource(dataSource: IRestaurantsDummyDataSource) : IRestaurantsDataSource

    @Binds
    @PerActivity
    abstract fun provideRestaurantRepository(repository: RestaurantsRepository): IRestaurantsRepository

    @Binds
    @PerActivity
    abstract fun provideFavoritesDataSource(dataSource: IFavoritesLocalDataSource) : IFavoritesDataSource

    @Binds
    @PerActivity
    abstract fun provideFavoritesRepository(repository: FavoritesRepository): IFavoritesRepository
}
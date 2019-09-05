package com.eslam.takeaway.restaurants.di

import com.eslam.takeaway.di.AppComponent
import com.eslam.takeaway.di.PerActivity
import com.eslam.takeaway.restaurants.presentation.RestaurantsViewModel
import dagger.Component

/**
 * The component whose lifetime is attached to the Overview screen activity lifetime
 */
@PerActivity
@Component(dependencies = [AppComponent::class], modules = [RestaurantModule::class])
interface RestaurantComponent {
    fun inject(viewModel: RestaurantsViewModel)
}
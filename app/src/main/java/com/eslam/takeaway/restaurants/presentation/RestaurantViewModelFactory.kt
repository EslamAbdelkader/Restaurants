package com.eslam.takeaway.restaurants.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.eslam.takeaway.application.TakeAwayApplication
import com.eslam.takeaway.restaurants.di.DaggerRestaurantComponent

/**
 * Factory class for [RestaurantsViewModel]
 */
@Suppress("UNCHECKED_CAST")
class RestaurantViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        val viewModel = RestaurantsViewModel()

        val appComponent = TakeAwayApplication.context.component
        DaggerRestaurantComponent.builder()
            .appComponent(appComponent)
            .build()
            .inject(viewModel)

        viewModel.initData()

        return viewModel as T
    }
}
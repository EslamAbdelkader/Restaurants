package com.eslam.takeaway.di

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

/**
 * The component whose lifetime is attached to the application's lifetime
 */
@Singleton
@Component
interface AppComponent {
    fun context(): Context

    @Component.Builder
    interface Builder {
        fun build(): AppComponent

        @BindsInstance
        fun context(context: Context): Builder
    }
}
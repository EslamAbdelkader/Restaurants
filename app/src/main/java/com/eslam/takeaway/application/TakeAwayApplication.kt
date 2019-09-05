package com.eslam.takeaway.application

import android.annotation.SuppressLint
import android.app.Application
import com.eslam.takeaway.di.AppComponent
import com.eslam.takeaway.di.DaggerAppComponent

/**
 * Application class
 */
class TakeAwayApplication : Application() {
    lateinit var component: AppComponent

    override fun onCreate() {
        super.onCreate()
        context = this
        component = DaggerAppComponent.builder()
            .context(context)
            .build()
    }

    companion object {
        /**
         * Not leaking, keeping the application context static, to provide for Dependency Injection
         */
        @SuppressLint("StaticFieldLeak")
        lateinit var context: TakeAwayApplication
    }
}
package com.bertan.esocial

import android.app.Application
import com.bertan.esocial.di.appModule
import com.bertan.esocial.di.dataModule
import com.bertan.esocial.di.domainModule
import com.bertan.esocial.di.presentationModule
import org.koin.android.ext.android.startKoin
import timber.log.Timber

class AppApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        setupKoin()
        setupTimber()
    }

    private fun setupKoin() {
        startKoin(
            this,
            listOf(
                domainModule,
                dataModule,
                presentationModule,
                appModule
            )
        )
    }

    private fun setupTimber() {
        Timber.plant(Timber.DebugTree())
    }
}
package com.bertan.esocial

import android.app.Application
import com.bertan.esocial.di.appModule
import com.bertan.esocial.di.dataModule
import com.bertan.esocial.di.domainModule
import com.bertan.esocial.di.presentationModule
import org.koin.android.ext.android.startKoin

class AppApplication : Application() {
    override fun onCreate() {
        super.onCreate()
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
}
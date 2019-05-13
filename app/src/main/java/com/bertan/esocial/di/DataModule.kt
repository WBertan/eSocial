package com.bertan.esocial.di

import com.bertan.data.local.repository.LocalDataSourceImpl
import com.bertan.data.remote.repository.RemoteDataSourceImpl
import com.bertan.data.remote.service.JSONPlaceholderServiceFactory
import com.bertan.data.repository.DataRepository
import com.bertan.data.repository.LocalDataSource
import com.bertan.data.repository.RemoteDataSource
import com.bertan.data.store.LocalDataStore
import com.bertan.data.store.RemoteDataStore
import com.bertan.domain.repository.Repository
import com.bertan.esocial.BuildConfig
import org.koin.dsl.module.Module
import org.koin.dsl.module.module

val dataModule: Module = module {
    single {
        JSONPlaceholderServiceFactory.build(
            isDebug = BuildConfig.DEBUG
        )
    }

    single {
        RemoteDataSourceImpl(
            service = get()
        ) as RemoteDataSource
    }
    single {
        RemoteDataStore(
            remoteDataSource = get()
        )
    }

    single {
        LocalDataSourceImpl(
            context = get()
        ) as LocalDataSource
    }
    single {
        LocalDataStore(
            localDataSource = get()
        )
    }

    single {
        DataRepository(
            localDataStore = get<LocalDataStore>(),
            remoteDataStore = get<RemoteDataStore>()
        ) as Repository
    }
}
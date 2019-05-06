package com.bertan.esocial.di

import com.bertan.domain.executor.SchedulerExecutor
import com.bertan.esocial.ui.executor.UiSchedulerExecutor
import org.koin.dsl.module.Module
import org.koin.dsl.module.module

val appModule: Module = module {
    single { this }
    single { UiSchedulerExecutor as SchedulerExecutor }
}
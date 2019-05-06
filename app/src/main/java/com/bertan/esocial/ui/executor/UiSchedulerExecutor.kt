package com.bertan.esocial.ui.executor

import com.bertan.domain.executor.SchedulerExecutor
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers

object UiSchedulerExecutor : SchedulerExecutor {
    override val scheduler: Scheduler
        get() = AndroidSchedulers.mainThread()
}
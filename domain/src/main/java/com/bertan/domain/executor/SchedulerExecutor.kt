package com.bertan.domain.executor

import io.reactivex.Scheduler

interface SchedulerExecutor {
    val scheduler: Scheduler
}
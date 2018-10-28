package com.slgunz.root.androidarticle.utils.scheduler

import io.reactivex.Scheduler

interface BaseSchedulerProvider {
    fun computation(): Scheduler
    fun io(): Scheduler
    fun ui(): Scheduler
}
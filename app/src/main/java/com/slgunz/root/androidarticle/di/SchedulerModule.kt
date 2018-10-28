package com.slgunz.root.androidarticle.di

import com.slgunz.root.androidarticle.utils.scheduler.BaseSchedulerProvider
import com.slgunz.root.androidarticle.utils.scheduler.SchedulerProvider
import dagger.Binds
import dagger.Module

@Module
abstract class SchedulerModule {
    @Binds
    abstract fun provideScheduler(schedulerProvider: SchedulerProvider): BaseSchedulerProvider
}
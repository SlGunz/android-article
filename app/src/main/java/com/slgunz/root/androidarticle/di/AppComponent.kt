package com.slgunz.root.androidarticle.di

import android.app.Application
import com.slgunz.root.androidarticle.ArticleApplication
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(AppModule::class, ActivitiesBindingModule::class))
interface AppComponent : AndroidComponent<ArticleApplication> {

    @Component.Builder
    interface Builder : AndroidComponent.Builder<AppComponent> {
        @BindsInstance
        fun application(application: Application): Builder
    }
}
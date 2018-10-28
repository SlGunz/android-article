package com.slgunz.root.androidarticle.di

import com.slgunz.root.androidarticle.ArticleApplication
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(AppModule::class, ActivitiesBindingModule::class))
interface AppComponent : AndroidComponent<ArticleApplication>
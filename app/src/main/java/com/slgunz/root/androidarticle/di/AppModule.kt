package com.slgunz.root.androidarticle.di

import com.slgunz.root.androidarticle.data.remote.ArticleService
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

@Module(includes = [SchedulerModule::class])
class AppModule {
    @Provides
    fun provideArticleService(): ArticleService {
        val retrofit = Retrofit.Builder()
            .baseUrl(ArticleService.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()

        return retrofit.create(ArticleService::class.java)
    }
}
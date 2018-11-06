package com.slgunz.root.androidarticle.di

import android.app.Application
import android.content.Context
import com.slgunz.root.androidarticle.data.remote.ArticleService
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

@Module(includes = [SchedulerModule::class])
class AppModule {
    @Provides
    fun provideArticleService(): ArticleService {
        val client = OkHttpClient.Builder()
            .readTimeout(60, TimeUnit.SECONDS)
            .build()

        val retrofit = Retrofit.Builder()
            .client(client)
            .baseUrl(ArticleService.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()

        return retrofit.create(ArticleService::class.java)
    }

    @Provides
    fun provideContext(application: Application): Context = application.applicationContext
}
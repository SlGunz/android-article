package com.slgunz.root.androidarticle

import android.app.Activity
import android.app.Application
import com.slgunz.root.androidarticle.di.AndroidComponent
import com.slgunz.root.androidarticle.di.DaggerAppComponent
import javax.inject.Inject

class ArticleApplication : Application() {

    @Inject
    lateinit var injectedComponents: Map<Class<out Activity>, @JvmSuppressWildcards AndroidComponent.Builder<*>>

    override fun onCreate() {
        super.onCreate()
        DaggerAppComponent.create().inject(this)
    }

    fun componentBuilder(aClass: Class<out Activity>): AndroidComponent.Builder<*>? {
        return injectedComponents[aClass]
    }
}
package com.slgunz.root.androidarticle.ui.base

import android.app.Activity
import android.support.v7.app.AppCompatActivity
import com.slgunz.root.androidarticle.ArticleApplication
import com.slgunz.root.androidarticle.di.AndroidComponent


abstract class BaseActivity : AppCompatActivity() {

    fun <T : Activity> inject(activity: T) {
        component(activity).inject(activity)
    }

    @Suppress("UNCHECKED_CAST")
    fun <A : Activity, C : AndroidComponent<A>> component(activity: A): C {
        val componentBuilder = (((application as ArticleApplication)
            .componentBuilder(activity.javaClass)) as AndroidComponent.Builder<C>?)
        if (componentBuilder != null) {
            return componentBuilder.build()
        }
        throw NoSuchElementException()
    }
}
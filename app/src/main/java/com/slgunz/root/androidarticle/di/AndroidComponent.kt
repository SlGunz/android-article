package com.slgunz.root.androidarticle.di

interface AndroidComponent<in T> {
    fun inject(instance: T)

    interface Builder<out U: AndroidComponent<*>>{
        fun build(): U
    }
}
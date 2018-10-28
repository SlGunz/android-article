package com.slgunz.root.androidarticle.di

import android.app.Activity
import dagger.MapKey
import kotlin.reflect.KClass


@MapKey
internal annotation class ActivityKey(val value: KClass<out Activity>)
package com.slgunz.root.androidarticle.di

import com.slgunz.root.androidarticle.ui.articlelist.ArticleListActivity
import com.slgunz.root.androidarticle.ui.articlelist.ArticleListSubcomponent
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module(subcomponents = arrayOf(ArticleListSubcomponent::class))
abstract class ActivitiesBindingModule {
    @Binds
    @IntoMap
    @ActivityKey(ArticleListActivity::class)
    abstract fun bindArticleListActivity(builder: ArticleListSubcomponent.Builder): AndroidComponent.Builder<*>
}
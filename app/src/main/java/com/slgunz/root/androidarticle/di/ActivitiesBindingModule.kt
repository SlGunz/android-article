package com.slgunz.root.androidarticle.di

import com.slgunz.root.androidarticle.ui.articledetail.ArticleDetailActivity
import com.slgunz.root.androidarticle.ui.articledetail.ArticleDetailSubcomponent
import com.slgunz.root.androidarticle.ui.articlelist.ArticleListActivity
import com.slgunz.root.androidarticle.ui.articlelist.ArticleListSubcomponent
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module(subcomponents = arrayOf(ArticleListSubcomponent::class, ArticleDetailSubcomponent::class))
abstract class ActivitiesBindingModule {
    @Binds
    @IntoMap
    @ActivityKey(ArticleListActivity::class)
    abstract fun bindArticleListActivity(builder: ArticleListSubcomponent.Builder): AndroidComponent.Builder<*>

    @Binds
    @IntoMap
    @ActivityKey(ArticleDetailActivity::class)
    abstract fun bindArticleDetailActivity(builder: ArticleDetailSubcomponent.Builder): AndroidComponent.Builder<*>
}
package com.slgunz.root.androidarticle.ui.articlelist

import com.slgunz.root.androidarticle.di.ActivityScope
import com.slgunz.root.androidarticle.di.AndroidComponent
import dagger.Subcomponent


@ActivityScope
@Subcomponent(modules = [ArticleListModule::class])
interface ArticleListSubcomponent : AndroidComponent<ArticleListActivity> {
    @Subcomponent.Builder
    interface Builder : AndroidComponent.Builder<ArticleListSubcomponent>
}
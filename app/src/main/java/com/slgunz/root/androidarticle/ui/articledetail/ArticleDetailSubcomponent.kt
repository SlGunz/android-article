package com.slgunz.root.androidarticle.ui.articledetail

import com.slgunz.root.androidarticle.di.ActivityScope
import com.slgunz.root.androidarticle.di.AndroidComponent
import dagger.Subcomponent


@ActivityScope
@Subcomponent(modules = [ArticleDetailModule::class])
interface ArticleDetailSubcomponent : AndroidComponent<ArticleDetailActivity> {
    @Subcomponent.Builder
    interface Builder : AndroidComponent.Builder<ArticleDetailSubcomponent>
}
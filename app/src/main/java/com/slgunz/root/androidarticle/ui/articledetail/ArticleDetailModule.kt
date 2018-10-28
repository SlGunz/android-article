package com.slgunz.root.androidarticle.ui.articledetail

import com.slgunz.root.androidarticle.di.ActivityScope
import dagger.Binds
import dagger.Module


@Module
abstract class ArticleDetailModule {
    @Binds
    @ActivityScope
    abstract fun providePresenter(presenter: ArticleDetailPresenter): ArticleDetailContract.Presenter
}
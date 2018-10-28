package com.slgunz.root.androidarticle.ui.articlelist

import com.slgunz.root.androidarticle.di.ActivityScope
import dagger.Binds
import dagger.Module


@Module
abstract class ArticleListModule {
    @Binds
    @ActivityScope
    abstract fun providePresenter(presenter: ArticleListPresenter): ArticleListContract.Presenter
}
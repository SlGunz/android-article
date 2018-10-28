package com.slgunz.root.androidarticle.ui.articlelist

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.slgunz.root.androidarticle.data.ArticleDataManager
import com.slgunz.root.androidarticle.data.model.Article
import com.slgunz.root.androidarticle.ui.TestSchedulerProvider
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.anyInt
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class ArticleListEndToEndTest {


    lateinit var dataManager: ArticleDataManager

    lateinit var view: ArticleListFragment

    lateinit var presenter: ArticleListPresenter

    lateinit var articles: MutableList<Article>

    lateinit var article: Article

    val error = "error"


    @Before
    fun initialization() {
        view = mock()
        dataManager = mock()
        article = mock()
        articles = mutableListOf(article)
        presenter = ArticleListPresenter(dataManager, TestSchedulerProvider())
    }

    @Test
    fun whenActivityBecomesVisible_thenGetArticles() {
        whenever(dataManager.getArticles()).thenReturn(Single.just(articles))

        presenter.subscribe(view)
        verify(view).setLoadIndicator(true)
        verify(dataManager).getArticles()
        verify(view).showArticles(articles)
        verify(view).setLoadIndicator(false)
    }

    @Test
    fun whenActivityBecomesVisible_thenShowChildScreenErrorMessage() {
        whenever(dataManager.getErrorMessage()).thenReturn(error)

        presenter.subscribe(view)
        verify(dataManager).getErrorMessage()
        verify(view).showErrorMessage(error)
    }

    @Test
    fun whenSelectArticle_thenOpenArticleDetail() {
        presenter.setView(view)

        presenter.openArticleDetail(anyInt())
        verify(dataManager).setSelectedArticleItem(anyInt())
        verify(view).showArticleDetail()
    }

    @Test
    fun whenLoadRemoteDataException_thenShowErrorMessage() {
        whenever(dataManager.getArticles()).thenReturn(Single.error(Throwable(error)))

        presenter.subscribe(view)
        verify(view).setLoadIndicator(true)
        verify(dataManager).getArticles()
        verify(view).showErrorMessage(error)
        verify(view).setLoadIndicator(false)
    }

}
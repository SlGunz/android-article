package com.slgunz.root.androidarticle.ui.articledetail

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
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class ArticleDetailEndToEndTest {

    lateinit var presenter: ArticleDetailPresenter

    lateinit var dataManager: ArticleDataManager

    lateinit var view: ArticleDetailFragment

    lateinit var article: Article

    val error = "error"

    @Before
    fun initialization() {
        view = mock()
        dataManager = mock()
        article = mock()
        presenter = ArticleDetailPresenter(dataManager, TestSchedulerProvider())
    }

    @Test
    fun whenActivityBecomesVisible_thenGetArticle(){
        whenever(dataManager.getSelectedArticleItem()).thenReturn(Single.just(article))

        presenter.subscribe(view)
        verify(view).setLoadIndicator(true)
        verify(dataManager).getSelectedArticleItem()
        verify(view).setLoadIndicator(false)
        verify(view).showArticle(article)
    }

    @Test
    fun whenLoadArticleGetException_thenShowErrorMessage(){
        whenever(dataManager.getSelectedArticleItem()).thenReturn(Single.error(Throwable(error)))

        presenter.subscribe(view)
        verify(view).setLoadIndicator(true)
        verify(dataManager).getSelectedArticleItem()
        verify(view).setLoadIndicator(false)
        // save an error message in DataManager to show it the in parent screen
        verify(dataManager).setErrorMessage(error)
        verify(view).showArticleList()

    }
}
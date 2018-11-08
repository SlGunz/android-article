package com.slgunz.root.androidarticle.ui.articlelist

import android.support.annotation.VisibleForTesting
import com.slgunz.root.androidarticle.data.ArticleDataManager
import com.slgunz.root.androidarticle.data.model.Article
import com.slgunz.root.androidarticle.utils.scheduler.BaseSchedulerProvider
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject


class ArticleListPresenter @Inject
constructor(var dataManager: ArticleDataManager, val scheduler: BaseSchedulerProvider) :
    ArticleListContract.Presenter {

    private var view: ArticleListContract.View? = null

    private val disposableList = CompositeDisposable()

    override fun openArticleDetail(articleId: Int) {
        dataManager.setSelectedArticleItem(articleId)
        view?.showArticleDetail()
    }

    override fun subscribe(view: ArticleListContract.View) {
        this.view = view
        activityDetailException(dataManager.getErrorMessage())
        loadArticles()
    }

    override fun unsubscribe() {
        disposableList.dispose()
    }

    private fun loadArticles() {
        view?.setLoadIndicator(true)
        val disposable = dataManager.getArticles()
            .subscribeOn(scheduler.io())
            .observeOn(scheduler.ui())
            .subscribe(
                // onSuccess
                { articles: MutableList<Article> ->
                    view?.showArticles(articles.toList())
                    view?.setLoadIndicator(false)
                },
                // onError
                { throwable: Throwable? ->
                    articlesException(throwable)
                    view?.setLoadIndicator(false)
                })

        if (disposable != null) {
            disposableList.add(disposable)
        }
    }

    private fun articlesException(throwable: Throwable?) {
        view?.showErrorMessage(throwable?.message ?: "Load articles exception")
    }

    private fun activityDetailException(message: String?) {
        if (message != null) {
            view?.showErrorMessage(message)
        }
    }

    override fun updateArticles() {
        dataManager.requireRemoteUpdate()
        loadArticles()
    }

    @VisibleForTesting
    fun setView(view: ArticleListContract.View) {
        this.view = view
    }
}
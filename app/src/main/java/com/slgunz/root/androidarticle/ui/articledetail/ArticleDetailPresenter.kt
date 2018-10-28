package com.slgunz.root.androidarticle.ui.articledetail

import com.slgunz.root.androidarticle.data.ArticleDataManager
import com.slgunz.root.androidarticle.data.model.Article
import com.slgunz.root.androidarticle.utils.scheduler.BaseSchedulerProvider
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject


class ArticleDetailPresenter @Inject
constructor(private val dataManager: ArticleDataManager, private val scheduler: BaseSchedulerProvider) :
    ArticleDetailContract.Presenter {

    var view: ArticleDetailContract.View? = null

    private val disposableList = CompositeDisposable()

    override fun subscribe(view: ArticleDetailContract.View) {
        this.view = view
        loadArticle()
    }

    override fun unsubscribe() = disposableList.dispose()

    private fun loadArticle() {
        view?.setLoadIndicator(true)
        val disposable = dataManager.getSelectedArticleItem()
            ?.subscribeOn(scheduler.io())
            ?.observeOn(scheduler.ui())
            ?.subscribe(
                { article: Article? ->
                    if (article != null) {
                        view?.showArticle(article)
                    }
                    view?.setLoadIndicator(false)
                },
                { throwable: Throwable? ->
                    showExceptionInParentScreen(throwable)
                    view?.setLoadIndicator(false)
                }
            )
        if (disposable != null) {
            disposableList.add(disposable)
        }
    }

    private fun showExceptionInParentScreen(throwable: Throwable?) {
        dataManager.setErrorMessage(throwable?.message ?: "Load article exception")
        view?.showArticleList()
    }
}
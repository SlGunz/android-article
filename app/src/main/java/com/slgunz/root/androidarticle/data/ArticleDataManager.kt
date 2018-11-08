package com.slgunz.root.androidarticle.data

import com.slgunz.root.androidarticle.data.local.LocalDataSource
import com.slgunz.root.androidarticle.data.model.Article
import com.slgunz.root.androidarticle.data.remote.ArticleService
import io.reactivex.Observable
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ArticleDataManager @Inject constructor(
    private val articleService: ArticleService,
    private val localDataSource: LocalDataSource
) {

    private var selectedArticleId: Int? = null
    private var errorMessage: String? = null

    private val cachedArticles: MutableMap<Int, Article> = mutableMapOf()
    private var needToUpdate: Boolean = false

    fun getArticles(): Single<MutableList<Article>> {
        if (!needToUpdate && !cachedArticles.isEmpty()) {
            return Observable.fromIterable(cachedArticles.values)
                .toList()
        }

        val remoteArticles = getAndSaveRemoteArticles()

        if (needToUpdate) {
            return remoteArticles
        }

        val localArticles = getAndCacheLocalArticles()
        return localArticles.concatWith(remoteArticles)
            .filter { !it.isEmpty() }
            .firstOrError()
    }

    private fun getAndCacheLocalArticles(): Observable<MutableList<Article>> {
        return localDataSource.getArticles()
            .flatMap { articles ->
                Observable.fromIterable(articles)
                    .doOnNext { article: Article ->
                        cachedArticles[article.id] = article
                    }.toList().toFlowable()
            }.toObservable()
    }

    private fun getAndSaveRemoteArticles(): Single<MutableList<Article>> {
        return articleService.articles()
            .flatMap { articles ->
                Observable.fromIterable(articles)
                    .doOnNext { article: Article ->
                        localDataSource.saveArticle(article)
                        cachedArticles[article.id] = article
                    }.toList()
            }.doOnSuccess { needToUpdate = false }
    }

    fun getArticleItem(articleId: Int): Single<Article>? {

        if (cachedArticles.contains(articleId)) {
            return Single.just(cachedArticles[articleId])
        }

        val localArticle: Observable<Article> = getArticleFromLocalDataSource(articleId)
        val remoteArticle: Observable<Article> = articleService.article(articleId)
            .doOnSuccess { article: Article? ->
                if (article != null) {
                    localDataSource.saveArticle(article)
                    cachedArticles[articleId] = article
                }
            }.toObservable()

        return Observable.concat(localArticle, remoteArticle)
            .firstOrError()
    }

    private fun getArticleFromLocalDataSource(articleId: Int): Observable<Article> {
        return localDataSource.getArticle(articleId.toString())
            .doOnNext { article: Article ->
                cachedArticles[articleId] = article
            }.firstElement().toObservable()
    }

    fun setSelectedArticleItem(articleId: Int) {
        selectedArticleId = articleId
    }

    fun getSelectedArticleItem(): Single<Article>? {
        if (selectedArticleId != null) {
            return selectedArticleId?.let { getArticleItem(it) }
        }
        return Single.error(Throwable("Article ID was not selected"))
    }

    fun setErrorMessage(message: String) {
        errorMessage = message
    }

    fun getErrorMessage(): String? {
        val message = errorMessage
        errorMessage = null
        return message
    }

    fun requireRemoteUpdate() {
        needToUpdate = true
    }
}
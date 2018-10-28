package com.slgunz.root.androidarticle.data

import com.slgunz.root.androidarticle.data.model.Article
import com.slgunz.root.androidarticle.data.remote.ArticleService
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ArticleDataManager @Inject constructor(private val articleService: ArticleService) {

    private var selectedArticleId: Int? = null
    private var errorMessage: String? = null

    fun getArticles(): Single<MutableList<Article>>? {
        return articleService.articles()
    }

    fun getArticleItem(articleId: Int?): Single<Article>? {
        return articleService.article(articleId)
    }

    fun setSelectedArticleItem(articleId: Int) {
        selectedArticleId = articleId
    }

    fun getSelectedArticleItem(): Single<Article>? {
        return getArticleItem(selectedArticleId)
    }

    fun setErrorMessage(message: String) {
        errorMessage = message
    }

    fun getErrorMessage(): String? {
        val message = errorMessage
        errorMessage = null
        return message
    }
}
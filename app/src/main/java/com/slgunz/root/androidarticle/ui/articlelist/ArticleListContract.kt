package com.slgunz.root.androidarticle.ui.articlelist

import com.slgunz.root.androidarticle.data.model.Article

interface ArticleListContract {
    interface View {
        fun setLoadIndicator(active: Boolean)
        fun showArticles(articles: List<Article>)
        fun showArticleDetail()
        fun showErrorMessage(message: String)
    }

    interface Presenter {
        fun openArticleDetail(articleId: Int)
        fun subscribe(view: View)
        fun unsubscribe()
    }
}
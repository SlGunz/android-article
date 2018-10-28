package com.slgunz.root.androidarticle.ui.articledetail

import com.slgunz.root.androidarticle.data.model.Article

interface ArticleDetailContract {
    interface View {
        fun setLoadIndicator(active: Boolean)
        fun showArticle(article: Article)
        fun showArticleList()
    }

    interface Presenter {
        fun subscribe(view: View)
        fun unsubscribe()
    }
}
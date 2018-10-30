package com.slgunz.root.androidarticle.ui.articledetail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.slgunz.root.androidarticle.R
import com.slgunz.root.androidarticle.data.model.Article
import com.slgunz.root.androidarticle.di.ActivityScope
import com.slgunz.root.androidarticle.ui.articlelist.ArticleListActivity
import com.slgunz.root.androidarticle.utils.ActivityUtils
import kotlinx.android.synthetic.main.article_detail_frag.*
import javax.inject.Inject


@ActivityScope
class ArticleDetailFragment @Inject
constructor() : Fragment(), ArticleDetailContract.View {

    interface Callbacks {
        fun setArticleHeader(article: Article)
        fun setLoadingIndicator(active: Boolean)
    }

    @Inject
    lateinit var presenter: ArticleDetailContract.Presenter

    private var callbacks: Callbacks? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.article_detail_frag, container, false)
    }

    override fun onResume() {
        super.onResume()
        presenter.subscribe(this)
    }

    override fun onPause() {
        super.onPause()
        presenter.unsubscribe()
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        callbacks = context as Callbacks

    }

    override fun onDetach() {
        super.onDetach()
        callbacks = null
    }

    override fun setLoadIndicator(active: Boolean) {
        callbacks?.setLoadingIndicator(active)
    }

    override fun showArticle(article: Article) {
        callbacks?.setArticleHeader(article)
        textView_content.text = ActivityUtils.stripHtml(article.content)
    }

    override fun showArticleList() {
        startActivity(Intent(context, ArticleListActivity::class.java))
    }


}
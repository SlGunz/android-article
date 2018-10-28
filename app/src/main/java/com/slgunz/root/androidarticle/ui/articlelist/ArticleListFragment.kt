package com.slgunz.root.androidarticle.ui.articlelist

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import com.slgunz.root.androidarticle.R
import com.slgunz.root.androidarticle.data.model.Article
import com.slgunz.root.androidarticle.di.ActivityScope
import com.slgunz.root.androidarticle.ui.articledetail.ArticleDetailActivity
import com.slgunz.root.androidarticle.ui.common.ArticleAdapter
import kotlinx.android.synthetic.main.article_list_frag.*
import kotlinx.android.synthetic.main.article_list_frag.view.*
import javax.inject.Inject


@ActivityScope
class ArticleListFragment @Inject
constructor() : Fragment(), ArticleListContract.View {
    @Inject
    lateinit var presenter: ArticleListContract.Presenter

    lateinit var adapter: ArticleAdapter

    lateinit var progressBar: ProgressBar

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.article_list_frag, container, false)
        adapter = ArticleAdapter(mutableListOf(), context!!.applicationContext)
        root.recycleViewArticles.layoutManager = LinearLayoutManager(context!!)
        root.recycleViewArticles.adapter = adapter
        adapter.onclick = presenter::openArticleDetail
        this.progressBar = root.progressBar
        return root
    }

    override fun onResume() {
        super.onResume()
        presenter.subscribe(this)
    }

    override fun onPause() {
        super.onPause()
        presenter.unsubscribe()
    }

    override fun setLoadIndicator(active: Boolean) {
        progressBar.visibility = if (active) View.VISIBLE else View.INVISIBLE
    }

    override fun showArticles(articles: List<Article>) {
        adapter.setList(articles)
    }

    override fun showArticleDetail() {
        startActivity(Intent(context, ArticleDetailActivity::class.java))
    }

    override fun showErrorMessage(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }
}
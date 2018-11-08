package com.slgunz.root.androidarticle.ui.articlelist

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.*
import android.widget.Toast
import com.slgunz.root.androidarticle.R
import com.slgunz.root.androidarticle.data.model.Article
import com.slgunz.root.androidarticle.di.ActivityScope
import com.slgunz.root.androidarticle.ui.articledetail.ArticleDetailActivity
import com.slgunz.root.androidarticle.ui.common.ArticleAdapter
import kotlinx.android.synthetic.main.article_list_frag.view.*
import javax.inject.Inject


@ActivityScope
class ArticleListFragment @Inject
constructor() : Fragment(), ArticleListContract.View {

    interface Callbacks {
        fun setLoadingIndicator(active: Boolean)
    }

    @Inject
    lateinit var presenter: ArticleListContract.Presenter

    lateinit var adapter: ArticleAdapter

    private var callbacks: Callbacks? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.article_list_frag, container, false)
        adapter = ArticleAdapter(mutableListOf(), context!!.applicationContext)
        root.recycleViewArticles.layoutManager = LinearLayoutManager(context!!)
        root.recycleViewArticles.adapter = adapter
        adapter.onclick = presenter::openArticleDetail

        setHasOptionsMenu(true)
        return root
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater?.inflate(R.menu.activity_list_toolbar_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean = when (item?.itemId) {
        R.id.action_refresh -> {
            presenter.updateArticles()
            true
        }
        else -> {
            super.onOptionsItemSelected(item)
        }
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
package com.slgunz.root.androidarticle.ui.articledetail

import android.os.Bundle
import android.view.View
import com.bumptech.glide.Glide
import com.slgunz.root.androidarticle.R
import com.slgunz.root.androidarticle.data.model.Article
import com.slgunz.root.androidarticle.ui.base.BaseActivity
import com.slgunz.root.androidarticle.utils.ActivityUtils
import kotlinx.android.synthetic.main.article_detail_act.*
import javax.inject.Inject

class ArticleDetailActivity : BaseActivity(), ArticleDetailFragment.Callbacks {

    @Inject
    lateinit var fragmentProvider: dagger.Lazy<ArticleDetailFragment>

    override fun onCreate(savedInstanceState: Bundle?) {
        inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.article_detail_act)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        var fragment: ArticleDetailFragment? =
            supportFragmentManager.findFragmentById(R.id.contentFrame) as ArticleDetailFragment?
        if (fragment == null) {
            fragment = fragmentProvider.get()
            ActivityUtils.addFragmentToActivity(supportFragmentManager, fragment, R.id.contentFrame)
        }
    }

    override fun setArticleHeader(article: Article) {
        toolbar_layout.title = article.title
        Glide.with(this).load(article.previewImage).into(imageView_article)
    }

    override fun setLoadingIndicator(active: Boolean) {
        progressBar.visibility = if (active) View.VISIBLE else View.INVISIBLE
    }
}

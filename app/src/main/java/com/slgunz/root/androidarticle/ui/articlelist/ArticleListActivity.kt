package com.slgunz.root.androidarticle.ui.articlelist

import android.os.Bundle
import com.slgunz.root.androidarticle.R
import com.slgunz.root.androidarticle.ui.base.BaseActivity
import com.slgunz.root.androidarticle.utils.ActivityUtils
import kotlinx.android.synthetic.main.article_list_act.*
import javax.inject.Inject


class ArticleListActivity : BaseActivity() {

    @Inject
    lateinit var fragmentProvider: dagger.Lazy<ArticleListFragment>

    override fun onCreate(savedInstanceState: Bundle?) {
        inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.article_list_act)
        setSupportActionBar(toolbar)

        var fragment: ArticleListFragment? =
            supportFragmentManager.findFragmentById(R.id.contentFrame) as ArticleListFragment?
        if (fragment == null) {
            fragment = fragmentProvider.get()
            ActivityUtils.addFragmentToActivity(supportFragmentManager, fragment, R.id.contentFrame)
        }
    }
}

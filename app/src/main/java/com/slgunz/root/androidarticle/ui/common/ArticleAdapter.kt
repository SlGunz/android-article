package com.slgunz.root.androidarticle.ui.common

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.slgunz.root.androidarticle.R
import com.slgunz.root.androidarticle.data.model.Article
import com.slgunz.root.androidarticle.di.ActivityScope
import com.slgunz.root.androidarticle.utils.ActivityUtils
import kotlinx.android.synthetic.main.article_list_item.view.*
import javax.inject.Inject

@ActivityScope

class ArticleAdapter @Inject constructor(private val context: Context) :
    RecyclerView.Adapter<ViewHolder>() {

    private val articles: MutableList<Article> = mutableListOf()

    var onclick: ((Int) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.article_list_item, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return articles.count()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onclick = onclick
        holder.bindViewHolder(articles[position], context)
    }

    fun setList(collection: Collection<Article>) {
        articles.addAll(collection)
        notifyDataSetChanged()
    }

}

class ViewHolder(view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {
    private val textViewTitle: TextView? = view.textView_title
    private val textViewBrief: TextView? = view.textView_content
    private val imageViewPreview: ImageView? = view.imageView_preview
    private val textViewCreator: TextView? = view.textView_creator
    private val textViewPublicationDate: TextView? = view.textView_pubDate

    private var article: Article? = null

    var onclick: ((Int) -> Unit)? = null

    init {
        view.setOnClickListener(this)
    }

    fun bindViewHolder(article: Article, context: Context) {
        this.article = article
        textViewTitle?.text = article.title
        textViewBrief?.text = ActivityUtils.stripHtml(article.content)
        // TODO: holder.textViewCreator?.text = article.creator
        textViewPublicationDate?.text = article.pubDate

        if (imageViewPreview != null) {
            if (!article.previewImage.isBlank()) {
                Glide.with(context).load(article.previewImage).into(imageViewPreview)
            } else {
                Glide.with(context).clear(imageViewPreview)
            }
        }
    }

    override fun onClick(p0: View?) {
        val id = article?.id
        if (id != null) {
            onclick?.invoke(id)
        }
    }
}
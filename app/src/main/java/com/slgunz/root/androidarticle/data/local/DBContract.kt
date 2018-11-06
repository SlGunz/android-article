package com.slgunz.root.androidarticle.data.local

class DBContract {
    object ArticleEntry {
        const val TABLE_NAME = "article"
        // cols
        const val ID = "id"
        const val TITLE = "title"
        const val IMAGE = "image"
        const val CONTENT = "content"
        const val PUB_DATE = "pub_date"
        const val CREATOR = "creator"
    }
}
package com.slgunz.root.androidarticle.data.local

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.slgunz.root.androidarticle.data.local.DBContract.ArticleEntry

class ArticleDbHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(CREATE_ARTICLE_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {}

    companion object {
        const val DATABASE_NAME = "article.db"
        const val DATABASE_VERSION = 1

        const val CREATE_ARTICLE_TABLE = "CREATE TABLE ${ArticleEntry.TABLE_NAME} (" +
                "${ArticleEntry.ID} INTEGER PRIMARY KEY," +
                "${ArticleEntry.TITLE}," +
                "${ArticleEntry.IMAGE}," +
                "${ArticleEntry.CONTENT}," +
                "${ArticleEntry.PUB_DATE}," +
                "${ArticleEntry.CREATOR})"
    }

}
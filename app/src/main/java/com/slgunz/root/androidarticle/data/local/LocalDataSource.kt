package com.slgunz.root.androidarticle.data.local

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import com.slgunz.root.androidarticle.data.local.DBContract.ArticleEntry
import com.slgunz.root.androidarticle.data.model.Article
import com.slgunz.root.androidarticle.utils.scheduler.BaseSchedulerProvider
import com.squareup.sqlbrite2.BriteDatabase
import com.squareup.sqlbrite2.SqlBrite
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalDataSource @Inject constructor(context: Context, schedulerProvider: BaseSchedulerProvider) {

    private val databaseHelper: BriteDatabase = SqlBrite.Builder().build()
        .wrapDatabaseHelper(ArticleDbHelper(context), schedulerProvider.io())

    private val articleMapperFunction: (Cursor) -> Article = this::getArticle

    private fun Cursor.getStringColumn(entry: String): String = this.getString(getColumnIndexOrThrow(entry))

    private fun getArticle(cursor: Cursor): Article {
        val id = cursor.getStringColumn(ArticleEntry.ID)
        val title = cursor.getStringColumn(ArticleEntry.TITLE)
        val image = cursor.getStringColumn(ArticleEntry.IMAGE)
        val content = cursor.getStringColumn(ArticleEntry.CONTENT)
        val pubDate = cursor.getStringColumn(ArticleEntry.PUB_DATE)
        val creator = cursor.getStringColumn(ArticleEntry.CREATOR)

        return Article(
            id = id.toInt(),
            title = title,
            previewImage = image,
            content = content,
            pubDate = pubDate,
            creator = creator
        )
    }

    fun getArticles(): Flowable<List<Article>> {
        val sql = "SELECT * FROM ${ArticleEntry.TABLE_NAME}"

        return databaseHelper.createQuery(ArticleEntry.TABLE_NAME, sql)
            .mapToList(articleMapperFunction)
            .toFlowable(BackpressureStrategy.BUFFER)
    }

    fun getArticle(articleId: String): Flowable<Article> {
        val sql = "SELECT * FROM ${ArticleEntry.TABLE_NAME} WHERE ${ArticleEntry.ID} LIKE ?"

        return databaseHelper.createQuery(ArticleEntry.TABLE_NAME, sql, articleId)
            .mapToOne(articleMapperFunction)
            .toFlowable(BackpressureStrategy.BUFFER)
    }

    fun saveArticle(article: Article) {
        val values = ContentValues()
        values.put(ArticleEntry.ID, article.id)
        values.put(ArticleEntry.TITLE, article.title)
        values.put(ArticleEntry.IMAGE, article.previewImage)
        values.put(ArticleEntry.CONTENT, article.content)
        values.put(ArticleEntry.PUB_DATE, article.pubDate)
        values.put(ArticleEntry.CREATOR, article.creator)
        databaseHelper.insert(ArticleEntry.TABLE_NAME, values, SQLiteDatabase.CONFLICT_REPLACE)
    }

    fun deleteAllArticles() {
        databaseHelper.delete(ArticleEntry.TABLE_NAME, null)
    }

    fun deleteArticle(articleId: String) {
        val selection = "${ArticleEntry.ID} LIKE ?"
        val selectionArgs = arrayOf(articleId)
        databaseHelper.delete(ArticleEntry.TABLE_NAME, selection, selectionArgs.toString())

    }
}
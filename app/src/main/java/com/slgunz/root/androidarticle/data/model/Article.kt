package com.slgunz.root.androidarticle.data.model

import com.google.gson.annotations.SerializedName

data class Article(
    @SerializedName("id") val id: Int,
    @SerializedName("title") val title: String,
    @SerializedName("preview_image") val previewImage: String,
    @SerializedName("content") val content: String,
    @SerializedName("pub_date") val pubDate: String,
    @SerializedName("creator") val creator: String
)
package com.slgunz.root.androidarticle.data.model

import com.google.gson.annotations.SerializedName

data class Comment(
    @SerializedName("id") val id: Int,
    @SerializedName("message") val message: String,
    @SerializedName("pub_date") val pubDate: String,
    @SerializedName("owner") val owner: Int
)
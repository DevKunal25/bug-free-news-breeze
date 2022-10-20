package com.news.breeze.model

import com.google.gson.annotations.SerializedName

data class MySubArticle(@SerializedName("author") val publisherName:String,
                        @SerializedName("title") val title:String,
                        @SerializedName("content") val shortDescription:String,
                        @SerializedName("description") val longDescription:String,
                        @SerializedName("urlToImage") val image:String,
                        @SerializedName("publishedAt") val publishedDate:String)
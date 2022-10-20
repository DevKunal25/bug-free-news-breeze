package com.news.breeze.model

import com.google.gson.annotations.SerializedName

data class MyArticle(@SerializedName("status") val status:String,
                     @SerializedName("totalResults") val count:Int,
                     @SerializedName("articles") val articles:List<MySubArticle>)
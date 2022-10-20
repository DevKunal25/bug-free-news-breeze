package com.news.breeze.api

import com.news.breeze.model.MyArticle
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface MyApiInterface
{
    @GET("/v2/top-headlines?country=in&pageSize=100&apiKey=e66ebc7fdd27444fb4ceba4c726bd374")
    fun getData():Call<MyArticle>

    companion object
    {
        private var BASE_URL = "https://newsapi.org/"

        fun create(): MyApiInterface
        {
            val retrofit = Retrofit.Builder()
                                   .addConverterFactory(GsonConverterFactory.create())
                                   .baseUrl(BASE_URL)
                                   .build()

            return retrofit.create(MyApiInterface::class.java)
        }
    }
}
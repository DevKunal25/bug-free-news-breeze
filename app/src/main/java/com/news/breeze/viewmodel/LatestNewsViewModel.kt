package com.news.breeze.viewmodel

import androidx.lifecycle.ViewModel
import com.news.breeze.model.MyArticle

class LatestNewsViewModel : ViewModel()
{
    var latestNewsData:MyArticle? = null
}
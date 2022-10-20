package com.news.breeze.sharedPreference

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences

class SavedNewsDatabase(private val mContext:Context)
{
    companion object
    {
        private var data:SharedPreferences? = null
    }

    /*--This method is responsible for initializing the shared preference instance--*/

    fun initializeInstance()
    {
        if(data == null)
        {
            data = mContext.getSharedPreferences("news_breeze_database", MODE_PRIVATE)
        }
    }

    /*--This method is responsible for inserting data into shared preference--*/

    fun insertData(value:String)
    {
        data!!.edit().putString(getSize().toString(), value).apply()
    }

    /*--This method is responsible to return the whole data from shared preference--*/

    fun fetchData():List<String>
    {
        var i = 0
        val size = getSize()

        val dataList:MutableList<String> = MutableList(size){""}

        while(i < size)
        {
            dataList[i] = data!!.getString(i.toString(), "DEFAULT").toString()
            i++
        }

        return dataList
    }

    /*--This method is responsible to return the size of shared preference--*/

    fun getSize():Int
    {
        return data!!.all.keys.size
    }
}
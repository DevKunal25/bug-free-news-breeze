package com.news.breeze.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.news.breeze.R
import com.news.breeze.data.ReadNewsData

class SavedNewsAdapter(private val dataList:List<String>, private val mContext:Context) : RecyclerView.Adapter<SavedNewsAdapter.ViewHolder>()
{
    override fun onCreateViewHolder(parent:ViewGroup, viewType:Int):ViewHolder
    {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.layout_saved_news, parent, false))
    }

    override fun onBindViewHolder(holder:ViewHolder, position:Int)
    {
        /*--Here, we are binding data with the view holder of recycler view--*/

        val i = position*5

        Glide.with(mContext)
             .load(dataList[i+0])
             .into(holder.newsImage)

        holder.newsTitle.text = dataList[i+1]
        holder.newsPublisherName.text = dataList[i+3]
        holder.newsPubDate.text = dataList[i+4]

        /*--If user clicks on particular saved news, then we will load the ReadNewsFragment--*/

        holder.itemView.setOnClickListener {

            loadReadNewsFragment(position, it)
        }
    }

    override fun getItemCount():Int
    {
        return dataList.size/5
    }

    /*--This method is responsible to load ReadNewsFragment--*/

    private fun loadReadNewsFragment(position:Int, it:View)
    {
        val j = position*5

        ReadNewsData.newsImage = dataList[j+0]
        ReadNewsData.newsTitle = dataList[j+1]
        ReadNewsData.newsLongDes = dataList[j+2]
        ReadNewsData.newsPublisherName = dataList[j+3]
        ReadNewsData.newsPubDate = dataList[j+4]

        ReadNewsData.newsPreviousLocation = 2
        ReadNewsData.newsSaveStatus = 1

        Navigation.findNavController(it).navigate(R.id.actionSavedNewsFragmentToReadNewsFragment)
    }

    /*--This class is containing the required views of a view holder--*/

    class ViewHolder(view:View) : RecyclerView.ViewHolder(view)
    {
        val newsImage:AppCompatImageView = view.findViewById(R.id.ivNewsImageSavedNewsFragment)
        val newsTitle:AppCompatTextView = view.findViewById(R.id.tvNewsTitleSavedNewsFragment)
        val newsPublisherName:AppCompatTextView = view.findViewById(R.id.tvNewsPublisherNameSavedNewsFragment)
        val newsPubDate:AppCompatTextView = view.findViewById(R.id.tvNewsPubDateSavedNewsFragment)
    }
}
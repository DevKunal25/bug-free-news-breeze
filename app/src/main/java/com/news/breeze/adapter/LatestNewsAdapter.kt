package com.news.breeze.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.facebook.shimmer.ShimmerFrameLayout
import com.news.breeze.R
import com.news.breeze.data.ReadNewsData
import com.news.breeze.data.SaveNewsData
import com.news.breeze.model.MyArticle
import com.news.breeze.sharedPreference.SavedNewsDatabase

class LatestNewsAdapter(private val data:MyArticle, private val mContext:Context) : RecyclerView.Adapter<LatestNewsAdapter.ViewHolder>()
{
    private var savedNewsDatabase:SavedNewsDatabase? = null

    override fun onCreateViewHolder(parent:ViewGroup, viewType:Int):ViewHolder
    {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.layout_latest_news, parent, false))
    }

    override fun onBindViewHolder(holder:ViewHolder, position:Int)
    {
        setDataOnViews(holder, position)

        /*--If user clicks on read button, then we will load the ReadNewsFragment--*/

        holder.readNews.setOnClickListener {

            loadReadNewsFragment(holder, position, it)
        }

        /*--If user clicks on save button, then first of all we will check whether the
            particular news is already saved or not. If already saved, then we will not perform
            any operation. If not saved, then we will save that news into database*/

        holder.saveNews.setOnClickListener {

            if(holder.saveStatus.text.equals("Save"))
            {
                if(savedNewsDatabase == null)
                {
                    savedNewsDatabase = SavedNewsDatabase(mContext)
                    savedNewsDatabase!!.initializeInstance()
                }

                insertDataIntoDatabase(position)

                SaveNewsData.savedItemPositionList.add(position)
                notifyItemChanged(position)
            }
        }

        /*--Iterating while loop to check whether saved item(s) exist or not--*/

        var counter = 0

        while(counter < SaveNewsData.savedItemPositionList.size)
        {
            if(SaveNewsData.savedItemPositionList[counter] == position)
            {
                changeLookAndFeelOfSavedItem(holder)
                break
            }
            else
            {
                holder.saveNews.setCardBackgroundColor(ContextCompat.getColor(mContext, R.color.color_94C77D))
                holder.saveStatus.text = mContext.getString(R.string.save)
                holder.saveCard.setCardBackgroundColor(ContextCompat.getColor(mContext, R.color.color_C1C0BC))
                holder.saveIcon.background =  ContextCompat.getDrawable(mContext, R.drawable.ic_save_2)
            }
            counter++
        }
    }

    override fun getItemCount():Int
    {
        return data.count
    }

    /*--This method is responsible for setting data on views--*/

    private fun setDataOnViews(holder:ViewHolder, position:Int)
    {
        Glide.with(mContext)
             .load(data.articles[position].image)
             .placeholder(R.drawable.ic_placeholder)
             .into(holder.newsImage)

        holder.newsTitle.text = data.articles[position].title
        holder.newsShortDes.text = data.articles[position].shortDescription
        holder.newsPubDate.text = data.articles[position].publishedDate.substring(0, 10)

        holder.newsTitle.setBackgroundColor(ContextCompat.getColor(mContext, android.R.color.transparent))
        holder.newsShortDes.setBackgroundColor(ContextCompat.getColor(mContext, android.R.color.transparent))
        holder.newsPubDate.setBackgroundColor(ContextCompat.getColor(mContext, android.R.color.transparent))

        holder.shimmer.hideShimmer()
    }

    /*--This method is responsible to load ReadNewsFragment--*/

    private fun loadReadNewsFragment(holder:ViewHolder, position:Int, it:View)
    {
        ReadNewsData.newsPosition = position

        ReadNewsData.newsImage = data.articles[position].image
        ReadNewsData.newsTitle = data.articles[position].title
        ReadNewsData.newsLongDes = data.articles[position].longDescription
        ReadNewsData.newsPubDate = data.articles[position].publishedDate.substring(0, 10)
        ReadNewsData.newsPublisherName = data.articles[position].publisherName

        ReadNewsData.newsPreviousLocation = 1

        if(holder.saveStatus.text.equals("Saved"))
        {
            ReadNewsData.newsSaveStatus = 1
        }
        else
        {
            ReadNewsData.newsSaveStatus = 0
        }

        Navigation.findNavController(it).navigate(R.id.actionLatestNewsFragmentToReadNewsFragment)
    }

    /*--This method is responsible to insert data into database (Shared Preference)--*/

    private fun insertDataIntoDatabase(position:Int)
    {
        ReadNewsData.newsImage = data.articles[position].image
        ReadNewsData.newsLongDes = data.articles[position].longDescription
        ReadNewsData.newsPublisherName = data.articles[position].publisherName

        if(ReadNewsData.newsImage == "" || ReadNewsData.newsImage == null)
        {
            ReadNewsData.newsImage = ""
        }
        if(ReadNewsData.newsLongDes == "" || ReadNewsData.newsLongDes == null)
        {
            ReadNewsData.newsLongDes = mContext.getString(R.string.dummy_news_long_des)
        }
        if(ReadNewsData.newsPublisherName == "" || ReadNewsData.newsPublisherName == null)
        {
            ReadNewsData.newsPublisherName = mContext.getString(R.string.dummy_news_pub_name)
        }

        savedNewsDatabase!!.insertData(ReadNewsData.newsImage!!)
        savedNewsDatabase!!.insertData(data.articles[position].title)
        savedNewsDatabase!!.insertData(ReadNewsData.newsLongDes!!)
        savedNewsDatabase!!.insertData(ReadNewsData.newsPublisherName!!)
        savedNewsDatabase!!.insertData(data.articles[position].publishedDate.substring(0, 10))
    }

    /*--This method is responsible to change the look and feel of saved item--*/

    private fun changeLookAndFeelOfSavedItem(holder:ViewHolder)
    {
        holder.saveNews.setCardBackgroundColor(ContextCompat.getColor(mContext, R.color.color_0D000000))
        holder.saveStatus.text = mContext.getString(R.string.saved)
        holder.saveCard.setCardBackgroundColor(ContextCompat.getColor(mContext, R.color.color_94C77D))
        holder.saveIcon.background =  ContextCompat.getDrawable(mContext, R.drawable.ic_save_4)
    }

    /*--This class is containing the required views of a view holder--*/

    class ViewHolder(view:View) : RecyclerView.ViewHolder(view)
    {
        val newsImage:AppCompatImageView = view.findViewById(R.id.ivNewsImageLatestNewsFragment)
        val newsTitle:AppCompatTextView = view.findViewById(R.id.tvNewsTitleLatestNewsFragment)
        val newsShortDes:AppCompatTextView = view.findViewById(R.id.tvNewsShortDesLatestNewsFragment)
        val newsPubDate:AppCompatTextView = view.findViewById(R.id.tvNewsPubDateLatestNewsFragment)
        val readNews:CardView = view.findViewById(R.id.cvReadNewsLatestNewsFragment)
        val saveNews:CardView = view.findViewById(R.id.cvSaveNewsLatestNewsFragment)
        val saveStatus:AppCompatTextView = view.findViewById(R.id.tvSaveLatestNewsFragment)
        val saveCard:CardView = view.findViewById(R.id.cvSaveLatestNewsFragment)
        val saveIcon:AppCompatImageView = view.findViewById(R.id.ivSaveLatestNewsFragment)
        val shimmer:ShimmerFrameLayout = view.findViewById(R.id.sflShimmerLatestNewsFragment)
    }
}
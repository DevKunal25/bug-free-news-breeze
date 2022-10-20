package com.news.breeze.view

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.news.breeze.R
import com.news.breeze.data.ReadNewsData
import com.news.breeze.data.SaveNewsData
import com.news.breeze.databinding.FragmentReadNewsBinding
import com.news.breeze.sharedPreference.SavedNewsDatabase

class ReadNewsFragment : Fragment(R.layout.fragment_read_news)
{
    private lateinit var viewBinding:FragmentReadNewsBinding
    private var savedNewsDatabase:SavedNewsDatabase? = null

    override fun onViewCreated(view:View, savedInstanceState:Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)

        viewBinding = FragmentReadNewsBinding.bind(view)

        setDataOnViews()

        /*--If user clicks on save button, then first of all we will check whether the particular
            news is saved or not. If already saved, then we will not perform any operation. If not
            saved, then we will save that news into database. Along with the saving, we will change
            the look and feel of ReadNewsFragment UI--*/

        viewBinding.cvSaveNewsReadNewsFragment.setOnClickListener {

            if(viewBinding.tvSaveReadNewsFragment.text.equals("Save"))
            {
                if(savedNewsDatabase == null)
                {
                    savedNewsDatabase = SavedNewsDatabase(this.requireContext())
                    savedNewsDatabase!!.initializeInstance()
                }

                insertDataIntoDatabase(ReadNewsData.newsImage.toString())
                insertDataIntoDatabase(ReadNewsData.newsTitle.toString())
                insertDataIntoDatabase(ReadNewsData.newsLongDes.toString())
                insertDataIntoDatabase(ReadNewsData.newsPublisherName.toString())
                insertDataIntoDatabase(ReadNewsData.newsPubDate.toString())

                SaveNewsData.savedItemPositionList.add(ReadNewsData.newsPosition!!)

                changeLookAndFeelOfSavedItem()

                Snackbar.make(view, R.string.news_saved_message, Snackbar.LENGTH_SHORT).show()
            }
        }

        /*--Here, we are popping/removing ReadNewsFragment on clicking of back button.
            After popping, we will be back on LatestNewsFragment or on SavedNewsFragment.
            It will depend upon the condition--*/

        viewBinding.ivBackReadNewsFragment.setOnClickListener {

            if(ReadNewsData.newsPreviousLocation == 1)
            {
                findNavController().navigate(R.id.actionReadNewsFragmentToLatestNewsFragment)
            }
            else
            {
                findNavController().navigate(R.id.actionReadNewsFragmentToSavedNewsFragment)
            }
        }
    }

    /*--This method is responsible for setting data on ReadNewsFragment's views--*/

    private fun setDataOnViews()
    {
        Glide.with(this)
             .load(ReadNewsData.newsImage)
             .into(viewBinding.ivNewsImageReadNewsFragment)

        viewBinding.tvNewsTitleReadNewsFragment.text = ReadNewsData.newsTitle
        viewBinding.tvNewsPubDateReadNewsFragment.text = ReadNewsData.newsPubDate

        if(ReadNewsData.newsImage == "" || ReadNewsData.newsImage == null)
        {
            ReadNewsData.newsImage = ""
        }

        if(ReadNewsData.newsLongDes != "" && ReadNewsData.newsLongDes != null)
        {
            viewBinding.tvNewsLongDesReadNewsFragment.text = ReadNewsData.newsLongDes
        }
        else
        {
            ReadNewsData.newsLongDes = getString(R.string.dummy_news_long_des)
        }

        if(ReadNewsData.newsPublisherName != "" && ReadNewsData.newsPublisherName != null)
        {
            viewBinding.tvNewsPublisherNameReadNewsFragment.text = ReadNewsData.newsPublisherName
        }
        else
        {
            ReadNewsData.newsPublisherName = getString(R.string.dummy_news_pub_name)
        }

        /*--If news is already saved by LatestNewsFragment, then we will change the look and feel
            of ReadNewsFragment UI. Here, '1' means news is already saved--*/

        if(ReadNewsData.newsSaveStatus == 1)
        {
            changeLookAndFeelOfSavedItem()
        }
    }

    /*--This method is responsible to insert data into database (Shared Preference)--*/

    private fun insertDataIntoDatabase(value:String)
    {
        savedNewsDatabase!!.insertData(value)
    }

    /*--This method is responsible to change the look and feel of ReadNewsFragment UI--*/

    private fun changeLookAndFeelOfSavedItem()
    {
        viewBinding.cvSaveNewsReadNewsFragment.setCardBackgroundColor(ContextCompat.getColor(this.requireContext(), R.color.color_0D000000))
        viewBinding.tvSaveReadNewsFragment.text = getString(R.string.saved)
        viewBinding.ivSaveReadNewsFragment.background = ContextCompat.getDrawable(this.requireContext(), R.drawable.ic_save_4)
    }
}
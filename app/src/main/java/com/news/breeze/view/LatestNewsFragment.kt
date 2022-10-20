package com.news.breeze.view

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.news.breeze.R
import com.news.breeze.adapter.LatestNewsAdapter
import com.news.breeze.api.MyApiInterface
import com.news.breeze.databinding.FragmentLatestNewsBinding
import com.news.breeze.model.MyArticle
import com.news.breeze.viewmodel.LatestNewsViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LatestNewsFragment : Fragment(R.layout.fragment_latest_news)
{
    private lateinit var viewBinding:FragmentLatestNewsBinding
    private val latestNewsViewModel:LatestNewsViewModel by viewModels()

    override fun onViewCreated(view:View, savedInstanceState:Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)

        viewBinding = FragmentLatestNewsBinding.bind(view)

        /*--Here, we are following MVVM concept--*/

        if(latestNewsViewModel.latestNewsData == null)
        {
            hitAPI()
        }
        else
        {
            showNewsData()
        }

        /*--Here, we are moving to SavedNewsFragment when user clicks on saved news button--*/

        viewBinding.cvSavedNewsLatestNewsFragment.setOnClickListener {

            findNavController().navigate(R.id.actionLatestNewsFragmentToSavedNewsFragment)
        }
    }

    /*--This is a method which is responsible for hitting the API--*/

    private fun hitAPI()
    {
        val myApiInterface = MyApiInterface.create().getData()

        myApiInterface.enqueue(object:Callback<MyArticle>
        {
            override fun onResponse(call:Call<MyArticle>, response:Response<MyArticle>)
            {
                if(response.body() != null)
                {
                    latestNewsViewModel.latestNewsData = response.body()

                    showNewsData()
                }
            }

            override fun onFailure(call:Call<MyArticle>, t:Throwable)
            {
                Log.e("Retrofit", "Something went wrong")
            }
        })
    }

    /*--This is a method which will be showing the news data--*/

    private fun showNewsData()
    {
        viewBinding.clContainerLatestNewsFragment.visibility = View.VISIBLE
        viewBinding.pbProgressLatestNewsFragment.visibility = View.GONE

        viewBinding.rvContainerLatestNewsFragment.layoutManager = LinearLayoutManager(this.requireContext())
        viewBinding.rvContainerLatestNewsFragment.adapter = LatestNewsAdapter(latestNewsViewModel.latestNewsData!!, this.requireContext())
    }
}
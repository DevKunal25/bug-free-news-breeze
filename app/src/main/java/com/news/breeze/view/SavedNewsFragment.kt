package com.news.breeze.view

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.news.breeze.R
import com.news.breeze.adapter.SavedNewsAdapter
import com.news.breeze.databinding.FragmentSavedNewsBinding
import com.news.breeze.sharedPreference.SavedNewsDatabase
import com.news.breeze.viewmodel.SavedNewsViewModel

class SavedNewsFragment : Fragment(R.layout.fragment_saved_news)
{
    private lateinit var viewBinding:FragmentSavedNewsBinding
    private val savedNewsViewModel:SavedNewsViewModel by viewModels()

    override fun onViewCreated(view:View, savedInstanceState:Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)

        viewBinding = FragmentSavedNewsBinding.bind(view)

        /*--Here, we are following MVVM concept--*/

        if(savedNewsViewModel.dataList == null)
        {
            val savedNewsDatabase = SavedNewsDatabase(this.requireContext())
            savedNewsDatabase.initializeInstance()

            if(savedNewsDatabase.getSize() != 0)
            {
                savedNewsViewModel.dataList = savedNewsDatabase.fetchData()

                setData(savedNewsViewModel.dataList!!)
            }
        }
        else
        {
            setData(savedNewsViewModel.dataList!!)
        }

        /*--Here, we are popping/removing SavedNewsFragment on clicking of back button.
            After popping, we will be back on LatestNewsFragment--*/

        viewBinding.ivBackSavedNewsFragment.setOnClickListener {

            findNavController().navigate(R.id.actionSavedNewsFragmentToLatestNewsFragment)
        }
    }

    /*--This method is responsible to pass data into SavedNewsAdapter of SavedNewsFragment, if
        the size of shared preference is greater than 0--*/

    private fun setData(dataList:List<String>)
    {
        viewBinding.llNewsCartSavedNewsFragment.visibility = View.GONE
        viewBinding.cvSearchBarSavedNewsFragment.visibility = View.VISIBLE
        viewBinding.cvContainerSavedNewsFragment.visibility = View.VISIBLE

        viewBinding.rvContainerSavedNewsFragment.layoutManager = LinearLayoutManager(this.requireContext())
        viewBinding.rvContainerSavedNewsFragment.adapter  = SavedNewsAdapter(dataList, this.requireContext())
    }
}
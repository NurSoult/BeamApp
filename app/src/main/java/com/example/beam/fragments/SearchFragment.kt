package com.example.beam.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.beam.R
import com.example.beam.activities.MainActivity
import com.example.beam.adapters.MealsAdapter
import com.example.beam.databinding.FragmentSearchBinding
import com.example.beam.viewModel.HomeViewModel


class SearchFragment : Fragment() {

    private lateinit var binding: FragmentSearchBinding
    private lateinit var viewModel: HomeViewModel
    private lateinit var searchRecyclerViewAdapter: MealsAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = (activity as MainActivity).viewModel

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        prepareRecyclerView()

        binding.imgSearchArrow.setOnClickListener { searchMeals() }

        observeSearchedMealsLiveData()
    }

    private fun observeSearchedMealsLiveData() {
        viewModel.observeSearchedMealsLiveData().observe(viewLifecycleOwner, Observer { mealsList ->

            searchRecyclerViewAdapter.differ.submitList(mealsList)

        })
    }

    private fun searchMeals() {
        val searchQuery = binding.edittextSearchBox.text.toString()
        if (searchQuery.isNotEmpty()) {
            viewModel.searchMeals(searchQuery)
        }
    }

    private fun prepareRecyclerView() {
        searchRecyclerViewAdapter = MealsAdapter()
        binding.recViewSearchedMeals.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = searchRecyclerViewAdapter
        }
    }

}
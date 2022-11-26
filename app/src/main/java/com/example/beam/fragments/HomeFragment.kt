package com.example.beam.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.example.beam.databinding.FragmentHomeBinding
import com.example.beam.pojo.Meal
import com.example.beam.pojo.MealList
import com.example.beam.retrofit.RetrofitInstance
import com.example.beam.viewModel.HomeViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class HomeFragment : Fragment() {

    private lateinit var binding:FragmentHomeBinding
    private lateinit var homeMVVM:HomeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        homeMVVM = ViewModelProviders.of(this)[HomeViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater,container,false)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        homeMVVM.getRandomMeal()
        observerRandomMeal()

    }

    private fun observerRandomMeal() {
        homeMVVM.observeRandomMealLiveData().observe(viewLifecycleOwner, object : Observer<Meal> {
            override fun onChanged(t: Meal?) {
                Glide.with(this@HomeFragment)
                    .load(t!!.strMealThumb)
                    .into(binding.imgRandomMeal)
            }

        })
    }


}
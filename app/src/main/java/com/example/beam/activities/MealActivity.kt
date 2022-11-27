package com.example.beam.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Typeface
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.example.beam.R
import com.example.beam.databinding.ActivityMainBinding
import com.example.beam.databinding.ActivityMealBinding
import com.example.beam.fragments.HomeFragment
import com.example.beam.pojo.Meal
import com.example.beam.viewModel.MealViewModel
import com.google.android.material.appbar.CollapsingToolbarLayout

class MealActivity : AppCompatActivity() {
    private lateinit var binding:ActivityMealBinding
    private lateinit var mealId:String
    private lateinit var mealName:String
    private lateinit var mealThumb:String
    private lateinit var youtubeLink: String

    private lateinit var mealMVVM: MealViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMealBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mealMVVM = ViewModelProviders.of(this)[MealViewModel::class.java]

        getMealInfoFromIntent()

        setInfoViews()

        loadingCase()

        mealMVVM.getMealDetail(mealId)
        observerMealDetailsLiveData()

        onYoutubeImageClick()
    }

    private fun onYoutubeImageClick() {
        binding.imgYoutube.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(youtubeLink))
            startActivity(intent)
        }
    }

    private fun observerMealDetailsLiveData() {
        mealMVVM.observerMealDetailsLiveData().observe(this, object : Observer<Meal> {
            override fun onChanged(t: Meal?) {
                onResponseCase()
                val meal = t

                binding.textviewCategory.text = "Category: ${meal!!.strCategory}"
                binding.textviewCountry.text = "Area: ${meal!!.strArea}"
                binding.textviewInstructionText.text = meal.strInstructions

                youtubeLink = meal.strYoutube
            }
        })
    }

    private fun setInfoViews() { //передача изображении
        Glide.with(applicationContext)
            .load(mealThumb)
            .into(binding.imgMealDetail)

        binding.collapsingToolBar.title = mealName
        binding.collapsingToolBar.setCollapsedTitleTextColor(resources.getColor(R.color.white)) //only свернут
        binding.collapsingToolBar.setExpandedTitleColor(resources.getColor(R.color.white))
    }

    private fun getMealInfoFromIntent() {
        val intent = intent
        mealId = intent.getStringExtra(HomeFragment.MEAL_ID)!!
        mealName = intent.getStringExtra(HomeFragment.MEAL_NAME)!!
        mealThumb = intent.getStringExtra(HomeFragment.MEAL_THUMB)!!
    }

    private fun loadingCase() {
        binding.progressBar.visibility = View.VISIBLE
        binding.btnAddToFav.visibility = View.INVISIBLE
        binding.textviewInstruction.visibility = View.INVISIBLE
        binding.textviewCategory.visibility = View.INVISIBLE
        binding.textviewCountry.visibility = View.INVISIBLE
        binding.imgYoutube.visibility = View.INVISIBLE
    }

    private fun onResponseCase() {
        binding.progressBar.visibility = View.INVISIBLE
        binding.btnAddToFav.visibility = View.VISIBLE
        binding.textviewInstruction.visibility = View.VISIBLE
        binding.textviewCategory.visibility = View.VISIBLE
        binding.textviewCountry.visibility = View.VISIBLE
        binding.imgYoutube.visibility = View.VISIBLE

    }



}
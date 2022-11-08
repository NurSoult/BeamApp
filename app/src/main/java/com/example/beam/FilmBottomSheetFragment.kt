    package com.example.beam

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

private const val FILM_ID = "param1"

class FilmBottomSheetFragment : Fragment() {
    private var filmid: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            filmid = it.getString(FILM_ID)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_film_bottom_sheet, container, false)
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String) =
            FilmBottomSheetFragment().apply {
                arguments = Bundle().apply {
                    putString(FILM_ID, param1)
                }
            }
    }
}
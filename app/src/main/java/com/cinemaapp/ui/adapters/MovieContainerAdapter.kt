package com.cinemaapp.ui.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.cinemaapp.ui.fragments.MovieListFragment
import com.cinemaapp.ui.fragments.TvShowListFragment


/*
* Created By mabrouk on 06/04/19
* CinemaApp
*/

class MovieContainerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment =
        when (position){
            0 -> MovieListFragment.getInstance()
            1 -> TvShowListFragment.getInstance()
              else ->throw IllegalArgumentException("No Fragment Found ") as Throwable
        }

    override fun getCount(): Int =2
}
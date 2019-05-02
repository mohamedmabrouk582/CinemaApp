package com.cinemaapp.callBacks

import androidx.paging.PagedList
import com.cinemaapp.data.models.TvShow
import com.cinemaapp.ui.fragments.TvShowListFragment


/*
* Created By mabrouk on 06/04/19
* CinemaApp
*/

interface TvShowCallBack :BaseCallBack {
    fun loadAiringToday(data:PagedList<TvShow>)
    fun loadOnTheAir(data: PagedList<TvShow>)
    fun loadPopular(data: PagedList<TvShow>)
    fun loadTopRated(data: PagedList<TvShow>)
    fun getTvShowFragment() : TvShowListFragment
}
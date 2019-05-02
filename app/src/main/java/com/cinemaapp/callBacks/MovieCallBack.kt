package com.cinemaapp.callBacks

import androidx.paging.PagedList
import com.cinemaapp.data.models.Movie
import com.cinemaapp.ui.fragments.MovieListFragment


/*
* Created By mabrouk on 06/04/19
* CinemaApp
*/

interface MovieCallBack : BaseCallBack{
    fun loadPopular(data:PagedList<Movie>)
    fun loadPlaying(data:PagedList<Movie>)
    fun loadTopRated(data:PagedList<Movie>)
    fun loadUpComing(data:PagedList<Movie>)
    fun getMovieListFragment():MovieListFragment
}
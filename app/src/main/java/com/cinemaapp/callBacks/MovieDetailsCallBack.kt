package com.cinemaapp.callBacks

import androidx.paging.PagedList
import com.cinemaapp.data.models.*
import com.cinemaapp.ui.fragments.MovieDetailsFragment


/*
* Created By mabrouk on 20/04/19
* CinemaApp
*/

interface MovieDetailsCallBack : BaseCallBack {
    fun getMovieDetailsFragment() : MovieDetailsFragment
    fun loadImages(images:ArrayList<MovieImage>)
    fun loadSimilarMovies(movies:PagedList<Movie>)
    fun loadRecommendMovies(movies:PagedList<Movie>)
    fun loadVideos(videos:ArrayList<MovieTrailer>)
    fun loadMovieCast(casts:ArrayList<MovieCast>)
    fun loadCompany(company:ArrayList<ProductionCompanies>)
}
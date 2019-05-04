package com.cinemaapp.callBacks

import androidx.paging.PagedList
import com.cinemaapp.data.models.*
import com.cinemaapp.ui.fragments.TvShowDetailsFragment


/*
* Created By mabrouk on 04/05/19
* CinemaApp
*/

interface TvDetailsCallBack : BaseCallBack {
    fun getDetailsFragment() : TvShowDetailsFragment
    fun loadImages(data:ArrayList<MovieImage>)
    fun loadRecommended(data: PagedList<TvShow>)
    fun loadSimilar(data: PagedList<TvShow>)
    fun loadTrailares(data: ArrayList<MovieTrailer>)
    fun loadSeasons(data:ArrayList<TvShowSeason>)
    fun loadCreatedBy(data: ArrayList<TvShowCreated>)
    fun loadTvNetworks(data:ArrayList<TvNetWorks>)
    fun loadLastEpisodeAir(data:ArrayList<TvEpisode>)
    fun loadNextEpisodeAir(data:ArrayList<TvEpisode>)
    fun producationCompany(data: ArrayList<ProductionCompanies>)
    fun loadTvCast(data:ArrayList<MovieCast>)
}
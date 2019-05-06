package com.cinemaapp.viewModels.base

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.cinemaapp.callBacks.*
import com.cinemaapp.data.api.BaseApi
import com.cinemaapp.data.db.MovieDao
import com.cinemaapp.utils.executors.AppExecutors
import com.cinemaapp.viewModels.*


/*
* Created By mabrouk on 16/03/19
* KotilnApp
*/

class BaseViewModelFactory(val movieDao: MovieDao, val appExecutors: AppExecutors
    , val api: BaseApi, val application: Application
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        when{
            modelClass.isAssignableFrom(MovieListViewModel::class.java) -> MovieListViewModel<MovieCallBack>(application,api,appExecutors,movieDao) as T
            modelClass.isAssignableFrom(TvShowListViewModel::class.java) ->TvShowListViewModel<TvShowCallBack>(application,api, movieDao, appExecutors) as T
            modelClass.isAssignableFrom(MovieDetailsViewModel::class.java) ->MovieDetailsViewModel<MovieDetailsCallBack>(application,api,movieDao,appExecutors) as T
            modelClass.isAssignableFrom(TvDetailsViewModel::class.java) -> TvDetailsViewModel<TvDetailsCallBack>(application,api, movieDao) as T
            modelClass.isAssignableFrom(SearchViewModel::class.java) -> SearchViewModel<SearchCallBack>(application, api) as T
            else -> throw IllegalArgumentException("Your View Model Not Found")
        }
}

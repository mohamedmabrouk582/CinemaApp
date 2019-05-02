package com.cinemaapp.viewModels

import android.app.Application
import android.util.Log
import androidx.databinding.ObservableArrayList
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.cinemaapp.callBacks.MovieCallBack
import com.cinemaapp.data.api.BaseApi
import com.cinemaapp.data.db.MovieDao
import com.cinemaapp.data.enums.MovieType
import com.cinemaapp.data.models.Movie
import com.cinemaapp.data.models.MovieDataSource
import com.cinemaapp.data.models.response.MovieResponse
import com.cinemaapp.utils.executors.AppExecutors
import com.cinemaapp.utils.network.RequestListener
import com.cinemaapp.viewModels.base.BaseViewModel
import kotlinx.coroutines.*


/*
* Created By mabrouk on 06/04/19
* CinemaApp
*/

class MovieListViewModel<v : MovieCallBack>(val app:Application,val api:BaseApi,val appExecutors: AppExecutors,val movieDao: MovieDao) : BaseViewModel<v>(app) {
    val loaders:ObservableArrayList<Boolean> = ObservableArrayList()
    val errors:ObservableArrayList<String> = ObservableArrayList()

    init {
        setUp()
    }

    fun setUp(){
        loaders.add(0,false)// playing
        loaders.add(1,false)// popular
        loaders.add(2,false)// top rated
        loaders.add(3,false)// up coming

        errors.add(0,null)// playing
        errors.add(1,null)// popular
        errors.add(2,null)// top rated
        errors.add(3,null)// up coming
    }

    fun reqMovies(type:MovieType){
        var pos:Int=type.ordinal
        loaders[pos] = true
        errors[pos]=null
        val dataSource=MovieDataSource(api,type,app,object : RequestListener<MovieResponse>{
            override fun onResponse(data: MutableLiveData<MovieResponse>) {
               loaders[pos]=false
                GlobalScope.apply {
                    launch (Dispatchers.IO) {
                        data.value?.results?.forEach {
                            it.type=type
                             movieDao.insertMovie(it)
                        }
                    }
                }
            }

            override fun onError(msg: String) {
              loaders[pos]=false
              errors[pos]=msg
            }

            override fun onSessionExpired(msg: String) {
                loaders[pos]=false
                errors[pos]=msg
            }

            override fun onNetWorkError(msg: String) {
                loaders[pos]=false
                showLocalData(movieDao.getMovies(type),type)
                return
            }

        })

        val factory= object :DataSource.Factory<Int,Movie>(){
            override fun create(): DataSource<Int, Movie> =dataSource
        }
        showNetworkData(factory,type)
    }

   private fun showNetworkData(factory:DataSource.Factory<Int,Movie>,type: MovieType){
        val config=PagedList.Config.Builder()
            .setEnablePlaceholders(true)
            .setPageSize(MovieDataSource.PAGE_SIZE).build()
        analysisData(type,LivePagedListBuilder(factory,config).build())
    }

    fun showLocalData(factory:DataSource.Factory<Int,Movie>,type: MovieType){
        val config=PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setPageSize(MovieDataSource.PAGE_SIZE1).build()
        analysisData(type,LivePagedListBuilder(factory,config).build())
    }

   private fun analysisData(type: MovieType, data:LiveData<PagedList<Movie>>){
        data.observe(view.getMovieListFragment(), Observer {
            when(type){
                MovieType.now_playing->view.loadPlaying(it)
                MovieType.popular -> view.loadPopular(it)
                MovieType.top_rated-> view.loadTopRated(it)
                MovieType.upcoming-> view.loadUpComing(it)
            }
        })

       GlobalScope.async(Dispatchers.IO) {
           api.getMovieCasts(240)
       }
    }


}
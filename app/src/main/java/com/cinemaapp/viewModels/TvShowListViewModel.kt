package com.cinemaapp.viewModels

import android.app.Application
import androidx.databinding.ObservableArrayList
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.cinemaapp.callBacks.TvShowCallBack
import com.cinemaapp.data.api.BaseApi
import com.cinemaapp.data.db.MovieDao
import com.cinemaapp.data.enums.TvShowType
import com.cinemaapp.data.models.*
import com.cinemaapp.data.models.response.TvShowResponse
import com.cinemaapp.utils.executors.AppExecutors
import com.cinemaapp.utils.network.RequestListener
import com.cinemaapp.viewModels.base.BaseViewModel


/*
* Created By mabrouk on 06/04/19
* CinemaApp
*/

class TvShowListViewModel<v : TvShowCallBack>(val app:Application,val api:BaseApi,val movieDao:MovieDao ,val appExecutors: AppExecutors) : BaseViewModel<v>(app) {
    val loaders: ObservableArrayList<Boolean> = ObservableArrayList()
    val errors: ObservableArrayList<String> = ObservableArrayList()

    init {
        setUp()
    }

    fun setUp(){
        loaders.add(0,false)// airing Today
        loaders.add(1,false)// on th air
        loaders.add(2,false)// popular
        loaders.add(3,false)// top rated

        errors.add(0,null)// airing today
        errors.add(1,null)// on the air
        errors.add(2,null)// popular
        errors.add(3,null)// top rated
    }

    fun reqTvShow(type:TvShowType){
        val pos=type.ordinal
        loaders[pos]=true
        errors[pos]=null
        val dataSource=TvShowDataSource(app,type,api,object  : RequestListener<TvShowResponse>{
            override fun onResponse(data: MutableLiveData<TvShowResponse>) {
               loaders[pos]=false
                appExecutors.networkIO.execute {
                    data.value?.results?.forEach {
                        it.tv_type=type
                        movieDao.insertTvShow(it)
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
                showLocalData(movieDao.getTvShow(type),type)
            }
        })

        val factory=object : DataSource.Factory<Int, TvShow>() {
            override fun create(): DataSource<Int, TvShow> =dataSource
        }
        showNetworkData(factory,type)
    }



    fun showNetworkData(factory: DataSource.Factory<Int, TvShow>, type: TvShowType){
        val config= PagedList.Config.Builder()
            .setEnablePlaceholders(true)
            .setPageSize(TvShowDataSource.PAGE_SIZE).build()
        anylsisData(type, LivePagedListBuilder(factory,config).build())
    }

    fun showLocalData(factory: DataSource.Factory<Int, TvShow>, type: TvShowType){
        val config= PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setPageSize(MovieDataSource.PAGE_SIZE1).build()
        anylsisData(type, LivePagedListBuilder(factory,config).build())
    }

    fun anylsisData(type: TvShowType, data: LiveData<PagedList<TvShow>>){
        data.observe(view.getTvShowFragment(), Observer {
            when(type){
                TvShowType.airing_today->view.loadAiringToday(it)
                TvShowType.on_the_air -> view.loadOnTheAir(it)
                TvShowType.popular-> view.loadPopular(it)
                TvShowType.top_rated-> view.loadTopRated(it)
            }
        })


    }


}
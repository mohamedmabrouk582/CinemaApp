package com.cinemaapp.viewModels

import android.app.Application
import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.cinemaapp.R
import com.cinemaapp.callBacks.TvDetailsCallBack
import com.cinemaapp.data.api.BaseApi
import com.cinemaapp.data.db.MovieDao
import com.cinemaapp.data.enums.MovieRelatedType
import com.cinemaapp.data.models.MovieImage
import com.cinemaapp.data.models.TvRelatedShowsDataSource
import com.cinemaapp.data.models.TvShow
import com.cinemaapp.data.models.TvShowDataSource
import com.cinemaapp.data.models.response.MovieCastResponse
import com.cinemaapp.data.models.response.MovieImagesResponse
import com.cinemaapp.data.models.response.MovieTrailersResponse
import com.cinemaapp.data.models.response.TvShowResponse
import com.cinemaapp.utils.network.Request
import com.cinemaapp.utils.network.RequestListener
import com.cinemaapp.viewModels.base.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*
import kotlin.collections.ArrayList


/*
* Created By mabrouk on 04/05/19
* CinemaApp
*/

class TvDetailsViewModel<v : TvDetailsCallBack>( application: Application,val api:BaseApi,val movieDao: MovieDao) : BaseViewModel<v>(application){
    val loaders:ObservableArrayList<Boolean> = ObservableArrayList()
    val errors:ObservableArrayList<String> = ObservableArrayList()
    val mainLoader: ObservableBoolean = ObservableBoolean()
    val mainError: ObservableField<String> = ObservableField()
    val tvShowData:ObservableField<TvShow> = ObservableField()
    var tv_id: Long=0
    lateinit var tvShow:TvShow

    init {
        setUp()
    }

   private fun setUp(){
        loaders.add(0,false)//trailer
        loaders.add(1,false)//cast
        loaders.add(2,false)//recommend
        loaders.add(3,false)//similar

        errors.add(0,null)//trailer
        errors.add(1,null)//seasons
        errors.add(2,null)//createdBy
        errors.add(3,null)//lastAir
        errors.add(4,null)//nextAir
        errors.add(5,null)//networks
        errors.add(6,null)//cast
        errors.add(7,null)//recommend
        errors.add(8,null)//similar
    }

    fun reqTvShowDetails(tv_id:Long){
        this.tv_id=tv_id
        setUp()
        mainLoader.set(true)
        mainError.set(null)
        api.getTvDetails(tv_id)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(Request(getApplication(),object : RequestListener<TvShow>{
                override fun onResponse(data: MutableLiveData<TvShow>) {
                    if (data.value!=null){
                        mainLoader.set(false)
                        tvShow=data.value!!
                        tvShowData.set(tvShow)
                        loadTvShow()
                        MovieRelatedType.values().forEach {
                            reqTvShow(it)
                        }
                        reqTvTrailer()
                        reqImages()
                        reqTvCast()
                        updateTvShow()
                    }
                }

                override fun onError(msg: String) {
                    mainLoader.set(false)
                    mainError.set(msg)
                }

                override fun onSessionExpired(msg: String) {
                    mainLoader.set(false)
                    mainError.set(msg)
                }

                override fun onNetWorkError(msg: String) {
                    movieDao.getTvShow(tv_id).observe(view.getDetailsFragment(), Observer {
                        mainLoader.set(false)
                        loadTvShow()
                        MovieRelatedType.values().forEach {
                            errors[it.ordinal+7]=view.getDetailsFragment().getString(R.string.no_data_found)
                        }
                    })
                }

            }))
    }

    private fun loadTvShow(){
        if (tvShow.seasons?.size!=0){
            view.loadSeasons(tvShow.seasons!!)
        }else errors[1]=view.getDetailsFragment().getString(R.string.no_data_found)

        if (tvShow.created_by?.size !=0){
            view.loadCreatedBy(tvShow.created_by!!)
        } else errors[2]=view.getDetailsFragment().getString(R.string.no_data_found)

        if (tvShow.last_episode_to_air?.size!=0)
            view.loadLastEpisodeAir(tvShow.last_episode_to_air!!)
        else errors[3]=view.getDetailsFragment().getString(R.string.no_data_found)

        if (tvShow.next_episode_to_air?.size!=0)
            view.loadNextEpisodeAir(tvShow.next_episode_to_air!!)
        else errors[4]=view.getDetailsFragment().getString(R.string.no_data_found)

        if (tvShow.networks?.size!=0)
            view.loadTvNetworks(tvShow.networks!!)
        else errors[5]=view.getDetailsFragment().getString(R.string.no_data_found)
    }

    fun reqImages(){
        api.getTVImages(tv_id)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(Request(getApplication(),object : RequestListener<MovieImagesResponse>{
                override fun onResponse(data: MutableLiveData<MovieImagesResponse>) {
                    val images:ArrayList<MovieImage> = ArrayList()
                    if (data.value?.posters?.size!=0)
                        images.addAll(data.value?.posters!!)
                    if(data.value?.backdrops?.size!=0)
                        images.addAll(data.value?.backdrops!!)

                    if (images.size>0){
                        view.loadImages(images)
                        tvShow.tvImages=images
                        updateTvShow()
                    }

                }

                override fun onError(msg: String) {
                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }

                override fun onSessionExpired(msg: String) {
                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }

                override fun onNetWorkError(msg: String) {
                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }

            }))
    }

    fun reqTvTrailer(){
        loaders[0]=true
        loaders[0]=null
        api.getTvTrailers(tv_id)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(Request(getApplication(),object : RequestListener<MovieTrailersResponse>{
                override fun onResponse(data: MutableLiveData<MovieTrailersResponse>) {
                    if (data.value?.trailers_results?.size !=0){
                       loaders[0]=false
                        tvShow.tvTrailer=data.value?.trailers_results
                        view.loadTrailares(tvShow.tvTrailer!!)
                        updateTvShow()
                    }else
                        onError(view.getDetailsFragment().getString(R.string.no_data_found))
                }

                override fun onError(msg: String) {
                    loaders[0]=false
                    errors[0]=msg
                }

                override fun onSessionExpired(msg: String) {
                    loaders[0]=false
                    errors[0]=msg
                }

                override fun onNetWorkError(msg: String) {
                    loaders[0]=false
                    errors[0]=msg
                }

            }))
    }

    fun reqTvCast(){
        loaders[1]=true
        errors[6]=null
        api.getTvCast(tv_id)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(Request(getApplication(),object : RequestListener<MovieCastResponse>{
                override fun onResponse(data: MutableLiveData<MovieCastResponse>) {
                    if (data.value?.cast?.size !=0){
                        loaders[1]=false
                        tvShow.tvCast=data.value?.cast
                        view.loadTvCast(tvShow.tvCast!!)
                    }else
                        onError(view.getDetailsFragment().getString(R.string.no_data_found))
                }

                override fun onError(msg: String) {
                    loaders[1]=false
                    errors[6]=msg
                }

                override fun onSessionExpired(msg: String) {
                    loaders[1]=false
                    errors[6]=msg
                }

                override fun onNetWorkError(msg: String) {
                    loaders[1]=false
                    errors[6]=msg
                }

            }))
    }

    fun reqTvShow(type:MovieRelatedType){
        loaders[type.ordinal+2]=true
        errors[type.ordinal+7]=null
        val dataSource= TvRelatedShowsDataSource(getApplication(),tv_id,type,api,object : RequestListener<TvShowResponse>{
            override fun onResponse(data: MutableLiveData<TvShowResponse>) {
                loaders[type.ordinal+2]=false
            }

            override fun onError(msg: String) {
                loaders[type.ordinal+2]=false
                errors[type.ordinal+7]=msg
            }

            override fun onSessionExpired(msg: String) {
                loaders[type.ordinal+2]=false
                errors[type.ordinal+7]=msg
            }

            override fun onNetWorkError(msg: String) {
                loaders[type.ordinal+2]=false
                errors[type.ordinal+7]=msg
            }

        })

        val factory=object : DataSource.Factory<Int,TvShow>(){
            override fun create(): DataSource<Int, TvShow> =dataSource
        }

        val config= PagedList.Config.Builder()
            .setPageSize(TvRelatedShowsDataSource.PAGE_SIZE).build()
        LivePagedListBuilder(factory,config).build().observe(view.getDetailsFragment(), Observer {
            when(type){
                MovieRelatedType.recommendations -> view.loadRecommended(it)
                MovieRelatedType.similar ->view.loadSimilar(it)
            }
        })
    }

   private fun updateTvShow(){
       GlobalScope.launch (Dispatchers.IO){
           movieDao.updateTvShow(tvShow)
       }
   }

    fun close(){
        view.getDetailsFragment().activity?.onBackPressed()
    }


}
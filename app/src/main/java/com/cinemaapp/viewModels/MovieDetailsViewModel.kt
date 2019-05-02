package com.cinemaapp.viewModels

import android.app.Application
import android.util.Log
import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.cinemaapp.R
import com.cinemaapp.callBacks.MovieDetailsCallBack
import com.cinemaapp.data.api.BaseApi
import com.cinemaapp.data.db.MovieDao
import com.cinemaapp.data.enums.MovieRelatedType
import com.cinemaapp.data.models.Movie
import com.cinemaapp.data.models.MovieImage
import com.cinemaapp.data.models.RelatedMovieDataSource
import com.cinemaapp.data.models.response.MovieCastResponse
import com.cinemaapp.data.models.response.MovieImagesResponse
import com.cinemaapp.data.models.response.MovieResponse
import com.cinemaapp.data.models.response.MovieTrailersResponse
import com.cinemaapp.utils.executors.AppExecutors
import com.cinemaapp.utils.network.Request
import com.cinemaapp.utils.network.RequestListener
import com.cinemaapp.viewModels.base.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.*
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Response
import java.io.IOException
import java.util.concurrent.Executor
import java.util.concurrent.Executors
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine


/*
* Created By mabrouk on 20/04/19
* CinemaApp
*/

class MovieDetailsViewModel<v : MovieDetailsCallBack>(
    application: Application,
    val api: BaseApi,
    val movieDao: MovieDao,
    val appExecutors: AppExecutors
) : BaseViewModel<v>(application) {
    var movieId: Long=0
    lateinit var movie:Movie
    val loaders:ObservableArrayList<Boolean> = ObservableArrayList()
    val errors:ObservableArrayList<String> = ObservableArrayList()
    val mainLoader:ObservableBoolean = ObservableBoolean()
    val mainError:ObservableField<String> = ObservableField()
    val movieObser:ObservableField<Movie> = ObservableField()


    init {
        setUpLists()
    }

    private fun setUpLists(){
        loaders.add(0,false)//trailers
        loaders.add(1,false)//cast
        loaders.add(2,false)//recommend
        loaders.add(3,false)//similar
        loaders.add(4,false)//images

        errors.add(0,null)//trailers
        errors.add(1,null)//cast
        errors.add(2,null)//recommend
        errors.add(3,null)//similar
        errors.add(4,null)//images

    }


     @ExperimentalCoroutinesApi
     fun reqMovieDetails(movieId: Long){
        this.movieId=movieId
     mainLoader.set(true)
     mainError.set(null)
        setUpLists()
        api.getMovieDetails(movieId)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(Request(getApplication(),object : RequestListener<Movie>{
                override fun onResponse(data: MutableLiveData<Movie>) {
                    mainLoader.set(false)
                    movie=data.value!!
                    movieObser.set(movie)
                    if (movie.production_companies?.size!!>0)
                        view.loadCompany(movie.production_companies!!)
                    updateMovie()
                    MovieRelatedType.values().forEach {
                        reqMovie(it)
                    }
                    reqImages()
                    reqCast()
                    reqTrailer()
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
                    movieDao.getMovie(movieId).observe(view.getMovieDetailsFragment(), Observer {
                       movie=it
                       Log.d("fafafdfsdsd",movie.toString())
                        loadOffline()
                    })
                }

            }))
    }

    fun reqTrailer(){
      loaders[0] =true
        errors[0] = null
        api.getMovieTrailers(movieId)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(Request(getApplication(),object  : RequestListener<MovieTrailersResponse>{
                override fun onResponse(data: MutableLiveData<MovieTrailersResponse>) {
                    if ( data.value?.trailers_results?.size!! >0){
                        loaders[0] = false
                        view.loadVideos(data.value?.trailers_results!!)
                        movie.movieTrailer=data.value?.trailers_results
                        updateMovie()
                    }else
                    onError(view.getMovieDetailsFragment().getString(R.string.no_data_found))

                }

                override fun onError(msg: String) {
                    loaders[0] = false
                    errors[0] = msg
                }

                override fun onSessionExpired(msg: String) {
                    loaders[0] = false
                    errors[0] = msg                }

                override fun onNetWorkError(msg: String) {
                    loaders[0] = false
                    errors[0] = msg
                }

            }))
    }

    fun reqCast(){
      loaders[1] = true
        errors[1] = null
        api.getMovieCast(movieId)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(Request(getApplication(),object : RequestListener<MovieCastResponse>{
                override fun onResponse(data: MutableLiveData<MovieCastResponse>) {
                    Log.d("fafafdfsdsd","getMovieCast")
                    if (data.value?.cast?.size!! >0){
                       loaders[1] = false
                        view.loadMovieCast(data.value?.cast!!)
                        movie.movieCast=data.value?.cast
                        updateMovie()
                    }else
                        onError(view.getMovieDetailsFragment().getString(R.string.no_data_found))

                }

                override fun onError(msg: String) {
                     loaders[1] = false
                      errors[1] = msg
                }

                override fun onSessionExpired(msg: String) {
                    loaders[1] = false
                    errors[1] = msg
                }

                override fun onNetWorkError(msg: String) {
                    loaders[1] = false
                    errors[1] = msg
                }

            }))
    }

    fun reqImages(){
        loaders[4] = true
        errors[4] = null
        api.getMovieImages(movieId)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(Request(getApplication(),object : RequestListener<MovieImagesResponse>{
                override fun onResponse(data: MutableLiveData<MovieImagesResponse>) {
                    val images:ArrayList<MovieImage> = ArrayList()
                    Log.d("fafafdfsdsd","getMovieImages")
                    if (data.value?.backdrops?.size!! >0){
                        images.addAll(data.value?.backdrops!!)
                    }
                    if (data.value?.posters?.size!! >0){
                        images.addAll(data.value?.posters!!)
                    }

                    if (images.size >0) {
                        loaders[4] = false
                         view.loadImages(images)
                        movie.movieImages = images
                        updateMovie()
                    } else
                        onError(view.getMovieDetailsFragment().getString(R.string.no_data_found))
                }

                override fun onError(msg: String) {
                     loaders[4] = false
                      errors[4] = msg
                }

                override fun onSessionExpired(msg: String) {
                    loaders[4] = false
                    errors[4] = msg                }

                override fun onNetWorkError(msg: String) {
                    loaders[4] = false
                    errors[4] = msg
                }

            }))
    }

    fun reqMovie(type:MovieRelatedType){
        loaders[type.ordinal+2] = true
        errors[type.ordinal+2] = null
      val movieDataSource = RelatedMovieDataSource(movieId,api,type,getApplication(),object : RequestListener<MovieResponse>{
          override fun onResponse(data: MutableLiveData<MovieResponse>) {
                 loaders[type.ordinal+2] = false
              Log.d("fafafdfsdsd","RelatedMovieDataSource${type.name}")

          }

          override fun onError(msg: String) {
              loaders[type.ordinal+2] = false
              errors[type.ordinal+2] = msg
          }

          override fun onSessionExpired(msg: String) {
              loaders[type.ordinal+2] = false
              errors[type.ordinal+2] = msg
          }

          override fun onNetWorkError(msg: String) {
              loaders[type.ordinal+2] = false
          }
      })

        val factory =object : DataSource.Factory<Int,Movie>(){
            override fun create(): DataSource<Int, Movie> = movieDataSource
        }
        showOnlineData(factory,type)
    }

    fun showOnlineData(factory:DataSource.Factory<Int,Movie>,type: MovieRelatedType){
       val config= PagedList.Config.Builder()
           .setEnablePlaceholders(true)
           .setPageSize(RelatedMovieDataSource.PAGE_SIZE).build()
        analysisData(type,LivePagedListBuilder(factory,config).build())
    }

   private fun analysisData(type:MovieRelatedType,data:LiveData<PagedList<Movie>>){
        data.observe(view.getMovieDetailsFragment(), Observer {
            when(type){
                MovieRelatedType.recommendations -> view.loadRecommendMovies(it)
                MovieRelatedType.similar ->view.loadSimilarMovies(it)
            }
        })
    }

     private fun loadOffline(){
        if (movie.movieCast !=null)
            view.loadMovieCast(movie.movieCast!!)
         else errors[1] = view.getMovieDetailsFragment().getString(R.string.no_data_found)

         if (movie.movieTrailer !=null)
             view.loadVideos(movie.movieTrailer!!)
         else errors[0] = view.getMovieDetailsFragment().getString(R.string.no_data_found)

         if (movie.movieImages != null)
             view.loadImages(movie.movieImages!!)
         else errors[4] = view.getMovieDetailsFragment().getString(R.string.no_data_found)

         errors[2] = view.getMovieDetailsFragment().getString(R.string.no_internet_connection)
         errors[3] = view.getMovieDetailsFragment().getString(R.string.no_internet_connection)
     }

    private fun updateMovie(){
        GlobalScope.apply {
            launch (Dispatchers.IO){
                movieDao.updateMovie(movie)
            }
        }
    }

}
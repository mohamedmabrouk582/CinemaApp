package com.cinemaapp.ui.fragments

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProviders
import androidx.paging.PagedList
import com.cinemaapp.app.MyApp
import com.cinemaapp.callBacks.MovieCallBack
import com.cinemaapp.callBacks.MovieDetailsCallBack
import com.cinemaapp.databinding.MovieDetailsLayoutBinding
import com.cinemaapp.di.components.DaggerMovieDetailsComponent
import com.cinemaapp.ui.adapters.MovieAdapter
import com.cinemaapp.ui.adapters.MovieCastAdapter
import com.cinemaapp.ui.adapters.ProducationCompanyAdapter
import com.cinemaapp.ui.base.BaseFragment
import com.cinemaapp.utils.Constans
import com.cinemaapp.viewModels.MovieDetailsViewModel
import com.cinemaapp.viewModels.base.BaseViewModel
import com.cinemaapp.viewModels.base.BaseViewModelFactory
import javax.inject.Inject
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import com.cinemaapp.R
import com.cinemaapp.data.models.*
import com.cinemaapp.ui.adapters.MovieTrailerAdapter
import com.mabrouk.slideroval.DefaultSliderView
import com.mabrouk.slideroval.IndicatorAnimations
import com.mabrouk.slideroval.ScrollTimeType
import com.mabrouk.slideroval.SliderAnimations
import kotlinx.android.synthetic.main.movie_details_layout.view.*


/*
* Created By mabrouk on 07/04/19
* CinemaApp
*/

class MovieDetailsFragment : BaseFragment() , MovieDetailsCallBack, MovieAdapter.MovieListener {

    override fun movieTitle(title: String) {
//        activity.setSupportActionBar(mActionBarToolbar);
//        getSupportActionBar().setTitle("My title");
        activity?.actionBar?.title=title

    }

    @Inject
    lateinit var factory: BaseViewModelFactory
    lateinit var layoutBinding:MovieDetailsLayoutBinding
    lateinit var viewModel:MovieDetailsViewModel<MovieDetailsCallBack>

    val producationCompanyAdapter:ProducationCompanyAdapter by lazy {
       ProducationCompanyAdapter()
    }

    val recommendMovies:MovieAdapter by lazy {
        MovieAdapter(this)
    }

    val similarMovies:MovieAdapter by lazy {
        MovieAdapter(this)
    }

    val movieCastAdapter:MovieCastAdapter by lazy {
        MovieCastAdapter()
    }

    val movieTrailersAdapter:MovieTrailerAdapter by lazy {
        MovieTrailerAdapter(object : MovieTrailerAdapter.TrailerListener{
            override fun onTrailerClick(item: MovieTrailer) {
             viewModel.watchYoutubeVideo(context!!,item.trailer_key?:"")
            }
        })
    }

    fun clearAllAdapters(){
      producationCompanyAdapter.data.let { it.clear() }
      movieTrailersAdapter.data.let { it.clear() }
      movieCastAdapter.data.let { it.clear() }
      layoutBinding.imageSliders.clearSliderViews()
    }




    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        layoutBinding=DataBindingUtil.inflate(inflater, R.layout.movie_details_layout,container,false)
        return layoutBinding.root
    }

    override fun inject() {
        DaggerMovieDetailsComponent.builder()
            .appComponent(MyApp.get(activity!!).appComponent)
            .build().inject(this)
    }

    override fun initView() {
        viewModel=getViewModel(activity!!,factory)
        viewModel.attachView(this)
        layoutBinding.imageSliders.setIndicatorAnimation(IndicatorAnimations.THIN_WORM)
        layoutBinding.imageSliders.setSliderTransformAnimation(SliderAnimations.FIDGETSPINTRANSFORMATION)
        layoutBinding.imageSliders.setScrollTimeInSec(1,ScrollTimeType.SECOND)
        setUoAdapters()
        viewModel.reqMovieDetails(activity?.intent?.getLongExtra(Constans.MOVIEID,0)!!)
        layoutBinding.movieDetails= viewModel
    }

    private inline fun <reified T : BaseViewModel<MovieDetailsCallBack>> getViewModel(activity: FragmentActivity, factory: BaseViewModelFactory): T {
        return ViewModelProviders.of(activity,factory)[T::class.java]
    }

    override fun getMovieDetailsFragment(): MovieDetailsFragment = this


    override fun loadImages(images: ArrayList<MovieImage>) {
        images.forEach {
            val sliderView = DefaultSliderView(context)
            sliderView.imageUrl = it.poster_path1
            layoutBinding.imageSliders.addSliderView(sliderView)
        }
    }

    private fun setUoAdapters(){
        //trailer
        layoutBinding.moveTrailerRcv.layoutManager=LinearLayoutManager(context,LinearLayout.HORIZONTAL,false)
        layoutBinding.moveTrailerRcv.adapter=movieTrailersAdapter

        //producation
        layoutBinding.companyRecyclerView.layoutManager=LinearLayoutManager(context,LinearLayout.HORIZONTAL,false)
        layoutBinding.companyRecyclerView.adapter=producationCompanyAdapter

        layoutBinding.similarRcv.layoutManager=LinearLayoutManager(context,LinearLayout.HORIZONTAL,false)
        layoutBinding.similarRcv.adapter=similarMovies

        layoutBinding.recommendRcv.layoutManager=LinearLayoutManager(context,LinearLayout.HORIZONTAL,false)
        layoutBinding.recommendRcv.adapter=recommendMovies

        layoutBinding.castRcv.layoutManager=LinearLayoutManager(context,LinearLayout.HORIZONTAL,false)
        layoutBinding.castRcv.adapter=movieCastAdapter
    }

    override fun loadSimilarMovies(movies: PagedList<Movie>) {
       similarMovies.submitList(movies)
    }

    override fun loadRecommendMovies(movies: PagedList<Movie>) {
     recommendMovies.submitList(movies)
    }

    override fun loadVideos(videos: ArrayList<MovieTrailer>) {
      movieTrailersAdapter.setTrailers(videos)
    }

    override fun loadMovieCast(casts: ArrayList<MovieCast>) {
      movieCastAdapter.setMovieCast(casts)
    }

    override fun loadCompany(company: ArrayList<ProductionCompanies>) {
        producationCompanyAdapter.setProducation(company)
    }


    override fun onClickMovie(item: Movie) {
        clearAllAdapters()
        viewModel.reqMovieDetails(item.id)
    }




}
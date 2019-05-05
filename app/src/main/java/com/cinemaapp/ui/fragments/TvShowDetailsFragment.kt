package com.cinemaapp.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProviders
import androidx.paging.PagedList
import androidx.recyclerview.widget.LinearLayoutManager
import com.cinemaapp.R
import com.cinemaapp.app.MyApp
import com.cinemaapp.callBacks.MovieDetailsCallBack
import com.cinemaapp.callBacks.TvDetailsCallBack
import com.cinemaapp.data.models.*
import com.cinemaapp.databinding.MovieTrailerItemViewBinding
import com.cinemaapp.databinding.TvShowDetailsLayoutBinding
import com.cinemaapp.di.components.DaggerTvShowComponent
import com.cinemaapp.ui.adapters.*
import com.cinemaapp.ui.base.BaseFragment
import com.cinemaapp.utils.Constans
import com.cinemaapp.viewModels.TvDetailsViewModel
import com.cinemaapp.viewModels.base.BaseViewModel
import com.cinemaapp.viewModels.base.BaseViewModelFactory
import com.mabrouk.slideroval.DefaultSliderView
import com.mabrouk.slideroval.IndicatorAnimations
import com.mabrouk.slideroval.ScrollTimeType
import com.mabrouk.slideroval.SliderAnimations
import javax.inject.Inject


/*
* Created By mabrouk on 04/05/19
* CinemaApp
*/

class TvShowDetailsFragment : BaseFragment() , TvDetailsCallBack,
    TvShowListAdapter.TvShowListener {


    @Inject
    lateinit var factory: BaseViewModelFactory
    lateinit var layoutBinding: TvShowDetailsLayoutBinding
    lateinit var viewModel: TvDetailsViewModel<TvDetailsCallBack>
    val trailerAdapter: MovieTrailerAdapter by lazy {
        MovieTrailerAdapter(object : MovieTrailerAdapter.TrailerListener{
            override fun onTrailerClick(item: MovieTrailer) {
             viewModel.watchYoutubeVideo(context!!,item.trailer_key!!)
            }

        })
    }
    val seasonAdapter: TvShowSeasonAdapter by lazy {
        TvShowSeasonAdapter(object : TvShowSeasonAdapter.SeasonListener{
            override fun onSeasonClick(season: TvShowSeason) {
            }

        })
    }

    val networkAdapter:TvNetworkAdapter by lazy {
        TvNetworkAdapter(object : TvNetworkAdapter.NetworkListener{
            override fun onNetworkClick(season: TvNetWorks) {
            }

        })
    }
    val createdByAdapter:TvCreatedByAdapter by lazy {
        TvCreatedByAdapter(object : TvCreatedByAdapter.CreatedByListener{
            override fun onCreatedByClick(item: TvShowCreated) {
            }

        })
    }
    val similarAdapter: TvShowListAdapter by lazy {
        TvShowListAdapter(this@TvShowDetailsFragment)
    }

    val recommendedAdapter: TvShowListAdapter by lazy {
        TvShowListAdapter(this@TvShowDetailsFragment)
    }

    val producationAdapter:ProducationCompanyAdapter by lazy {
        ProducationCompanyAdapter()
    }

    val castAdapter:MovieCastAdapter by lazy {
        MovieCastAdapter()
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        layoutBinding=DataBindingUtil.inflate(inflater, R.layout.tv_show_details_layout,container,false)
        return layoutBinding.root
    }
    override fun inject() {
     DaggerTvShowComponent.builder()
         .appComponent(MyApp.get(activity!!).appComponent)
         .build().inject(this)
    }

    override fun initView() {
     viewModel=getViewModel(activity!!,factory)
     viewModel.attachView(this)
     setUpAdapter()
     viewModel.reqTvShowDetails(arguments?.getLong(Constans.TV_ID)?:0)
     layoutBinding.tvDetails=viewModel
    }

    fun setUpAdapter(){
        layoutBinding.imageSliders.setScrollTimeInSec(1,ScrollTimeType.SECOND)
        layoutBinding.imageSliders.setIndicatorAnimation(IndicatorAnimations.THIN_WORM)
        layoutBinding.imageSliders.setSliderTransformAnimation(SliderAnimations.CLOCK_SPINTRANSFORMATION)
        //trailer
        layoutBinding.moveTrailerRcv.layoutManager= LinearLayoutManager(context, LinearLayout.HORIZONTAL,false)
        layoutBinding.moveTrailerRcv.adapter=trailerAdapter

        //seasons
        layoutBinding.seasonsRcv.layoutManager=LinearLayoutManager(context,LinearLayout.HORIZONTAL,false)
        layoutBinding.seasonsRcv.adapter=seasonAdapter

        //created By
        layoutBinding.createdRcv.layoutManager=LinearLayoutManager(context, LinearLayout.HORIZONTAL,false)
        layoutBinding.createdRcv.adapter=createdByAdapter

        //network
        layoutBinding.networksRcv.layoutManager=LinearLayoutManager(context, LinearLayout.HORIZONTAL,false)
        layoutBinding.networksRcv.adapter=networkAdapter

        //recommended
        layoutBinding.recommendRcv.layoutManager=LinearLayoutManager(context, LinearLayout.HORIZONTAL,false)
        layoutBinding.recommendRcv.adapter=recommendedAdapter

        //similar
        layoutBinding.similarRcv.layoutManager=LinearLayoutManager(context, LinearLayout.HORIZONTAL,false)
        layoutBinding.similarRcv.adapter=similarAdapter

        //cast
        layoutBinding.castRcv.layoutManager=LinearLayoutManager(context, LinearLayout.HORIZONTAL,false)
        layoutBinding.castRcv.adapter=castAdapter

        //producation
        layoutBinding.companyRecyclerView.layoutManager=LinearLayoutManager(context, LinearLayout.HORIZONTAL,false)
        layoutBinding.companyRecyclerView.adapter=producationAdapter

    }


    override fun getDetailsFragment(): TvShowDetailsFragment =this

    override fun loadImages(data: ArrayList<MovieImage>) {
        data.forEach {
            val sliderView= DefaultSliderView(context)
            sliderView.imageUrl=it.poster_path1
            layoutBinding.imageSliders.addSliderView(sliderView)
        }
    }

    override fun loadRecommended(data: PagedList<TvShow>) {
        recommendedAdapter.submitList(data)
    }

    override fun loadSimilar(data: PagedList<TvShow>) {
        similarAdapter.submitList(data)
    }

    override fun loadTrailares(data: ArrayList<MovieTrailer>) {
        trailerAdapter.setTrailers(data)
    }

    override fun loadSeasons(data: ArrayList<TvShowSeason>) {
        seasonAdapter.setData(data)
    }

    override fun loadCreatedBy(data: ArrayList<TvShowCreated>) {
        createdByAdapter.setData(data)
    }

    override fun loadTvNetworks(data: ArrayList<TvNetWorks>) {
        networkAdapter.setData(data)
    }

    override fun producationCompany(data: ArrayList<ProductionCompanies>) {
        producationAdapter.setProducation(data)
    }

    override fun loadTvCast(data: ArrayList<MovieCast>) {
        castAdapter.setMovieCast(data)
    }

    private inline fun <reified T : BaseViewModel<TvDetailsCallBack>> getViewModel(activity: FragmentActivity, factory: BaseViewModelFactory): T {
        return ViewModelProviders.of(activity,factory)[T::class.java]
    }

    override fun onTvShowClick(item: TvShow) {

    }

}
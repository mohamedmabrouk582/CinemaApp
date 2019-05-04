package com.cinemaapp.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProviders
import androidx.paging.PagedList
import com.cinemaapp.R
import com.cinemaapp.app.MyApp
import com.cinemaapp.callBacks.MovieDetailsCallBack
import com.cinemaapp.callBacks.TvDetailsCallBack
import com.cinemaapp.data.models.*
import com.cinemaapp.databinding.MovieTrailerItemViewBinding
import com.cinemaapp.databinding.TvShowDetailsLayoutBinding
import com.cinemaapp.di.components.DaggerTvShowComponent
import com.cinemaapp.ui.base.BaseFragment
import com.cinemaapp.utils.Constans
import com.cinemaapp.viewModels.TvDetailsViewModel
import com.cinemaapp.viewModels.base.BaseViewModel
import com.cinemaapp.viewModels.base.BaseViewModelFactory
import javax.inject.Inject


/*
* Created By mabrouk on 04/05/19
* CinemaApp
*/

class TvShowDetailsFragment : BaseFragment() , TvDetailsCallBack{
    @Inject
    lateinit var factory: BaseViewModelFactory
    lateinit var layoutBinding: TvShowDetailsLayoutBinding
    lateinit var viewModel: TvDetailsViewModel<TvDetailsCallBack>

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
     layoutBinding.tvDetails=viewModel
    }


    override fun getDetailsFragment(): TvShowDetailsFragment =this

    override fun loadImages(data: ArrayList<MovieImage>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun loadRecommended(data: PagedList<TvShow>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun loadSimilar(data: PagedList<TvShow>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun loadTrailares(data: ArrayList<MovieTrailer>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun loadSeasons(data: ArrayList<TvShowSeason>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun loadCreatedBy(data: ArrayList<TvShowCreated>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun loadTvNetworks(data: ArrayList<TvNetWorks>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun loadLastEpisodeAir(data: ArrayList<TvEpisode>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun loadNextEpisodeAir(data: ArrayList<TvEpisode>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun producationCompany(data: ArrayList<ProductionCompanies>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun loadTvCast(data: ArrayList<MovieCast>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private inline fun <reified T : BaseViewModel<TvDetailsCallBack>> getViewModel(activity: FragmentActivity, factory: BaseViewModelFactory): T {
        return ViewModelProviders.of(activity,factory)[T::class.java]
    }
}
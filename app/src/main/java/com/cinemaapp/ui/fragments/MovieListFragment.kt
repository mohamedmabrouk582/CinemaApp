package com.cinemaapp.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProviders
import androidx.paging.PagedList
import androidx.recyclerview.widget.LinearLayoutManager
import com.cinemaapp.R
import com.cinemaapp.app.MyApp
import com.cinemaapp.callBacks.MovieCallBack
import com.cinemaapp.data.enums.DetailsType
import com.cinemaapp.data.enums.MovieType
import com.cinemaapp.data.models.Movie
import com.cinemaapp.databinding.MovieListFramentLayoutBinding
import com.cinemaapp.di.components.DaggerMovieListComponent
import com.cinemaapp.ui.activities.MovieDetailsActivity
import com.cinemaapp.ui.adapters.MovieAdapter
import com.cinemaapp.ui.base.BaseFragment
import com.cinemaapp.viewModels.MovieListViewModel
import com.cinemaapp.viewModels.base.BaseViewModel
import com.cinemaapp.viewModels.base.BaseViewModelFactory
import javax.inject.Inject


/*
* Created By mabrouk on 06/04/19
* CinemaApp
*/

class MovieListFragment : BaseFragment() , MovieCallBack , MovieAdapter.MovieListener{
    @Inject
    lateinit var factory:BaseViewModelFactory
    override fun getMovieListFragment(): MovieListFragment =this
    lateinit var layoutBinding: MovieListFramentLayoutBinding
    lateinit var popularAdapter:MovieAdapter
    lateinit var topRatedAdapter :MovieAdapter
    lateinit var playingNowAdapter:MovieAdapter
    lateinit var upComingAdapter :MovieAdapter
    lateinit var viewModel:MovieListViewModel<MovieCallBack>

    companion object {
        fun getInstance()= MovieListFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        layoutBinding=DataBindingUtil.inflate(inflater, R.layout.movie_list_frament_layout,container,false)
        return layoutBinding.root
    }

    override fun inject() {
        DaggerMovieListComponent.builder()
            .appComponent(MyApp.get(activity!!).appComponent)
            .build().inject(this)
    }

    override fun initView() {
       setUpAdapter()
       viewModel=getViewModel(activity!!,factory)
        viewModel.attachView(this)
        layoutBinding.movieList=viewModel
        MovieType.values().forEach {
                 viewModel.reqMovies(it)
        }
    }

    private fun setUpAdapter(){
        // popular
        popularAdapter= MovieAdapter(this)
        layoutBinding.popularRcv.layoutManager=LinearLayoutManager(context,LinearLayout.HORIZONTAL,false)
        layoutBinding.popularRcv.adapter=popularAdapter

        // Top Rated
        topRatedAdapter=MovieAdapter(this)
        layoutBinding.topRatedRcv.layoutManager=LinearLayoutManager(context,LinearLayout.HORIZONTAL,false)
        layoutBinding.topRatedRcv.adapter=topRatedAdapter

        // Playing Now
        playingNowAdapter= MovieAdapter(this)
        layoutBinding.playingNowRcv.layoutManager=LinearLayoutManager(context,LinearLayout.HORIZONTAL,false)
        layoutBinding.playingNowRcv.adapter=playingNowAdapter

        // UP Coming
        upComingAdapter= MovieAdapter(this)
        layoutBinding.upComingRcv.layoutManager=LinearLayoutManager(context,LinearLayout.HORIZONTAL,false)
        layoutBinding.upComingRcv.adapter=upComingAdapter
    }

    override fun loadPopular(data: PagedList<Movie>) {
        popularAdapter.submitList(data)
    }

    override fun loadPlaying(data: PagedList<Movie>) {
        playingNowAdapter.submitList(data)
    }

    override fun loadTopRated(data: PagedList<Movie>) {
        topRatedAdapter.submitList(data)
    }

    override fun loadUpComing(data: PagedList<Movie>) {
        upComingAdapter.submitList(data)
    }

    override fun onClickMovie(item: Movie) {
        MovieDetailsActivity.start(context!!,item.id,DetailsType.MOVIE)
    }

    private inline fun <reified T : BaseViewModel<MovieCallBack>> getViewModel(activity: FragmentActivity, factory: BaseViewModelFactory): T {
        return ViewModelProviders.of(activity,factory)[T::class.java]
    }
}
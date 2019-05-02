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
import com.cinemaapp.callBacks.TvShowCallBack
import com.cinemaapp.data.enums.TvShowType
import com.cinemaapp.data.models.TvShow
import com.cinemaapp.databinding.TvShowListLayoutBinding
import com.cinemaapp.di.components.DaggerTvShowListComponent
import com.cinemaapp.ui.adapters.TvShowListAdapter
import com.cinemaapp.ui.base.BaseFragment
import com.cinemaapp.viewModels.TvShowListViewModel
import com.cinemaapp.viewModels.base.BaseViewModel
import com.cinemaapp.viewModels.base.BaseViewModelFactory
import javax.inject.Inject


/*
* Created By mabrouk on 06/04/19
* CinemaApp
*/

class TvShowListFragment : BaseFragment() , TvShowCallBack , TvShowListAdapter.TvShowListener{
    @Inject
    lateinit var factory: BaseViewModelFactory
    lateinit var layoutBinding: TvShowListLayoutBinding
    lateinit var viewModel : TvShowListViewModel<TvShowCallBack>
    lateinit var airingTodayAdapter: TvShowListAdapter
    lateinit var onTheAirAdapter: TvShowListAdapter
    lateinit var popularAdapter: TvShowListAdapter
    lateinit var topRatedAdapter: TvShowListAdapter

    companion object {
        fun getInstance() = TvShowListFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        layoutBinding=DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.tv_show_list_layout,container,false)
        return layoutBinding.root
    }

    override fun initView() {
      setUpAdapters()
        viewModel=getViewModel(activity!!,factory)
        viewModel.attachView(this)
        layoutBinding.tvList=viewModel
        for (type in TvShowType.values())
            viewModel.reqTvShow(type)
    }

    fun  setUpAdapters(){
       // Airing Today
        airingTodayAdapter= TvShowListAdapter(this)
        layoutBinding.airingTodayRcv.layoutManager=LinearLayoutManager(context,LinearLayout.HORIZONTAL,false)
        layoutBinding.airingTodayRcv.adapter=airingTodayAdapter

        //on the Air
        onTheAirAdapter= TvShowListAdapter(this)
        layoutBinding.onTheAirRcv.layoutManager=LinearLayoutManager(context,LinearLayout.HORIZONTAL,false)
        layoutBinding.onTheAirRcv.adapter=onTheAirAdapter

        //popular
        popularAdapter= TvShowListAdapter(this)
        layoutBinding.popularRcv.layoutManager=LinearLayoutManager(context,LinearLayout.HORIZONTAL,false)
        layoutBinding.popularRcv.adapter=popularAdapter

        //top rated
        topRatedAdapter= TvShowListAdapter(this)
        layoutBinding.topRatedRcv.layoutManager=LinearLayoutManager(context,LinearLayout.HORIZONTAL,false)
        layoutBinding.topRatedRcv.adapter=topRatedAdapter

    }


    override fun loadAiringToday(data: PagedList<TvShow>) {
        airingTodayAdapter.submitList(data)
    }

    override fun loadOnTheAir(data: PagedList<TvShow>) {
        onTheAirAdapter.submitList(data)
    }

    override fun loadPopular(data: PagedList<TvShow>) {
        popularAdapter.submitList(data)
    }

    override fun loadTopRated(data: PagedList<TvShow>) {
        topRatedAdapter.submitList(data)
    }

    override fun getTvShowFragment(): TvShowListFragment =this

    override fun inject() {
        DaggerTvShowListComponent.builder()
            .appComponent(MyApp.get(activity!!).appComponent)
            .build().inject(this)
    }

    override fun onTvShowClick(item: TvShow) {
        Toast.makeText(context,item.toString(),Toast.LENGTH_SHORT).show()
    }

    private inline fun <reified T : BaseViewModel<TvShowCallBack>> getViewModel(activity: FragmentActivity, factory: BaseViewModelFactory): T {
        return ViewModelProviders.of(activity,factory)[T::class.java]
    }
}
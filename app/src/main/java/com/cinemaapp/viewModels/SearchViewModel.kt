package com.cinemaapp.viewModels

import android.app.Application
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import com.cinemaapp.R
import com.cinemaapp.callBacks.SearchCallBack
import com.cinemaapp.data.api.BaseApi
import com.cinemaapp.data.models.KeyWord
import com.cinemaapp.data.models.SearchResult
import com.cinemaapp.data.models.SearchResultDataSource
import com.cinemaapp.data.models.response.KeyWordResponse
import com.cinemaapp.data.models.response.SearchResultResponse
import com.cinemaapp.utils.network.Request
import com.cinemaapp.utils.network.RequestListener
import com.cinemaapp.viewModels.base.BaseViewModel
import com.mabrouk.loaderlib.RetryCallBack
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


/*
* Created By mabrouk on 06/05/19
* CinemaApp
*/

class SearchViewModel<v : SearchCallBack>(application: Application,val api: BaseApi) : BaseViewModel<v>(application) {
    val loader:ObservableBoolean = ObservableBoolean()
    val error:ObservableField<String> = ObservableField()
    val loadMore:ObservableBoolean= ObservableBoolean()
    lateinit var query:String
    var isMore:Boolean=false
    var keyWordPage:Int=1
    val callBack:RetryCallBack by lazy { object : RetryCallBack{
        override fun onRetry() {
            retry()
        }
    }
    }
    fun reqSearch(query:String){
        this.query=query
      isMore=false
      loader.set(true)
       error.set(null)
       val dataSource = SearchResultDataSource(getApplication(),api,query,object : RequestListener<SearchResultResponse> {
           override fun onResponse(data: MutableLiveData<SearchResultResponse>) {
               loader.set(false)
               isMore=true
           }

           override fun onError(msg: String) {
               if (isMore)return
               loader.set(false)
               error.set(msg)
           }

           override fun onSessionExpired(msg: String) {
               if (isMore)return
               loader.set(false)
               error.set(msg)
           }

           override fun onNetWorkError(msg: String) {
               if (isMore)return
               loader.set(false)
               error.set(msg)
           }

       })

        val factory= object : DataSource.Factory<Int,SearchResult>(){
            override fun create(): DataSource<Int, SearchResult> = dataSource
        }

        LivePagedListBuilder(factory,SearchResultDataSource.PageSize).build().observe(view.getSearchActivity(), Observer {
            view.loadSearchData(it)
        })
    }

    fun reqKeyWords(query:String,isLoad:Boolean){
        api.getKeyWords(query,keyWordPage)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(Request(getApplication(),object : RequestListener<KeyWordResponse>{
                override fun onResponse(data: MutableLiveData<KeyWordResponse>) {
                   if (data.value?.results?.size !=0) {
                       loadMore.set(true)
                       keyWordPage=data.value?.page!!
                       val items:ArrayList<String> = ArrayList()
                       data.value?.results?.forEach {
                           items.add((it).name)
                       }
                       if (isLoad)
                           view.loadMoreKeyWords(items)
                       else view.loadKeyWorkds(items)
                   }else{
                       loadMore.set(false)
                       onError(view.getSearchActivity().getString(R.string.no_data_found))
                   }

                }

                override fun onError(msg: String) {

                }

                override fun onSessionExpired(msg: String) {

                }

                override fun onNetWorkError(msg: String) {

                }

            }))
    }

    fun retry(){
        reqSearch(query)
    }
}
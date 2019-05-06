package com.cinemaapp.data.models

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.cinemaapp.data.api.BaseApi
import com.cinemaapp.data.models.response.SearchResultResponse
import com.cinemaapp.utils.network.Request
import com.cinemaapp.utils.network.RequestListener
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


/*
* Created By mabrouk on 06/05/19
* CinemaApp
*/

class SearchResultDataSource(val context: Context,val api: BaseApi,val query:String,val listener: RequestListener<SearchResultResponse>) : PageKeyedDataSource<Int,SearchResult>() {

    companion object{
        val FirstPage:Int=1
        val PageSize:Int=20
    }

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, SearchResult>) {
        api.getSearch(query, FirstPage)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(Request(context,object : RequestListener<SearchResultResponse>{
                override fun onResponse(data: MutableLiveData<SearchResultResponse>) {
                    if (data.value?.results?.size !=0){
                        listener.onResponse(data)
                        callback.onResult(data.value?.results!!,null,FirstPage+1)
                    }else onError("No Data Found")
                }

                override fun onError(msg: String) {
                    listener.onError(msg)
                }

                override fun onSessionExpired(msg: String) {
                    listener.onSessionExpired(msg)
                }

                override fun onNetWorkError(msg: String) {
                    listener.onNetWorkError(msg)
                }

            }))
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, SearchResult>) {
        api.getSearch(query, params.key)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(Request(context,object : RequestListener<SearchResultResponse>{
                override fun onResponse(data: MutableLiveData<SearchResultResponse>) {
                    if (data.value?.results?.size !=0){
                        listener.onResponse(data)
                        callback.onResult(data.value?.results!!,if(data.value?.total_pages!! >0) params.key+1 else null )
                    }else onError("No Data Found")
                }

                override fun onError(msg: String) {
                    listener.onError(msg)
                }

                override fun onSessionExpired(msg: String) {
                    listener.onSessionExpired(msg)
                }

                override fun onNetWorkError(msg: String) {
                    listener.onNetWorkError(msg)
                }

            }))
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, SearchResult>) {
        api.getSearch(query, params.key)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(Request(context,object : RequestListener<SearchResultResponse>{
                override fun onResponse(data: MutableLiveData<SearchResultResponse>) {
                    if (data.value?.results?.size !=0){
                        listener.onResponse(data)
                        callback.onResult(data.value?.results!!,if (params.key>1) params.key-1 else null)
                    }else onError("No Data Found")
                }

                override fun onError(msg: String) {
                    listener.onError(msg)
                }

                override fun onSessionExpired(msg: String) {
                    listener.onSessionExpired(msg)
                }

                override fun onNetWorkError(msg: String) {
                    listener.onNetWorkError(msg)
                }

            }))
    }
}
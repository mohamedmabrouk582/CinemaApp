package com.cinemaapp.data.models

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.cinemaapp.R
import com.cinemaapp.data.api.BaseApi
import com.cinemaapp.data.enums.TvShowType
import com.cinemaapp.data.models.response.TvShowResponse
import com.cinemaapp.utils.network.Request
import com.cinemaapp.utils.network.RequestListener
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


/*
* Created By mabrouk on 06/04/19
* CinemaApp
*/

class TvShowDataSource(val context: Context,val type:TvShowType,val api: BaseApi,val listener: RequestListener<TvShowResponse>) : PageKeyedDataSource<Int,TvShow>() {

    companion object {
        val FIRST_PAGE:Int=1
        val PAGE_SIZE:Int=30
        val PAGE_SIZE1:Int=20
    }

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, TvShow>) {
        api.getTvShow(type, FIRST_PAGE)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(Request(context,object : RequestListener<TvShowResponse>{
                override fun onResponse(data: MutableLiveData<TvShowResponse>) {
                    if (data.value?.results!=null && data.value?.results?.size!=0){
                        listener.onResponse(data)
                        callback.onResult(data.value?.results!!,null, FIRST_PAGE+1)
                    }else onError(context.getString(R.string.no_data_found))
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

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, TvShow>) {
        api.getTvShow(type, params.key)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(Request(context,object : RequestListener<TvShowResponse>{
                override fun onResponse(data: MutableLiveData<TvShowResponse>) {
                    if (data.value?.results!=null && data.value?.results?.size!=0){
                        listener.onResponse(data)
                        callback.onResult(data.value?.results!!,if (data.value?.total_pages!!>0) params.key+1 else null)
                    }else onError(context.getString(R.string.no_data_found))
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

            }))    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, TvShow>) {
        api.getTvShow(type,params.key)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(Request(context,object : RequestListener<TvShowResponse>{
                override fun onResponse(data: MutableLiveData<TvShowResponse>) {
                    if (data.value!=null && data.value?.results?.size!=0){
                        listener.onResponse(data)
                        callback.onResult(data.value!!.results,if (params.key>1) params.key-1 else null)
                    }else onError(context.getString(R.string.no_data_found))
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

            }))    }
}
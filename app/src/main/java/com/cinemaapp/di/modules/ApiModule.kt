package com.cinemaapp.di.modules

import com.cinemaapp.data.api.BaseApi
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


/*
* Created By mabrouk on 16/03/19
* KotilnApp
*/

@Module
class ApiModule(val baseUrl: String) {
    @Provides
    @Singleton
    fun getApi(retrofit: Retrofit): BaseApi = retrofit.create(BaseApi::class.java)

    @Provides
    @Singleton
    fun getRetrofit(client: OkHttpClient): Retrofit =
        Retrofit.Builder().baseUrl(baseUrl).client(client).addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build()

    @Provides
    @Singleton
    fun client(inter: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient.Builder().addInterceptor(inter).build()
    }

//    return new OkHttpClient.Builder().cache(cache).addInterceptor(interceptor).addNetworkInterceptor(provideCacheInterceptor()).addInterceptor(provideOfflineCacheInterceptor()).build();

    @Provides
    @Singleton
    fun getIntercaptor(): HttpLoggingInterceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
}

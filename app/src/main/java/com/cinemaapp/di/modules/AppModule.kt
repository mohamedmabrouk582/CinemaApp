package com.cinemaapp.di.modules

import com.cinemaapp.app.MyApp
import com.cinemaapp.data.api.BaseApi
import com.cinemaapp.data.db.MovieDao
import com.cinemaapp.utils.executors.AppExecutors
import com.cinemaapp.viewModels.base.BaseViewModelFactory
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


/*
* Created By mabrouk on 16/03/19
* KotilnApp
*/
@Module
class AppModule(var myApp: MyApp) {
    @Provides
    @Singleton
    fun viewModelFactory(movieDao: MovieDao, appExecutors: AppExecutors, api: BaseApi): BaseViewModelFactory =
        BaseViewModelFactory(movieDao, appExecutors, api, getApp())

    @Provides
    @Singleton
    fun getApp(): MyApp = myApp
}

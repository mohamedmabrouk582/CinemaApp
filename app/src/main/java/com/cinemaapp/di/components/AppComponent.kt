package com.cinemaapp.di.components

import com.cinemaapp.app.MyApp
import com.cinemaapp.data.api.BaseApi
import com.cinemaapp.di.modules.ApiModule
import com.cinemaapp.data.db.MovieDao
import com.cinemaapp.di.modules.RoomModule
import com.cinemaapp.di.modules.AppModule
import com.cinemaapp.di.modules.ExecutorsModule
import com.cinemaapp.utils.executors.AppExecutors
import com.cinemaapp.viewModels.base.BaseViewModelFactory
import dagger.Component
import javax.inject.Singleton


/*
* Created By mabrouk on 16/03/19
* KotilnApp
*/
@Singleton
@Component(modules = arrayOf(AppModule::class, ApiModule::class, ExecutorsModule::class, RoomModule::class))
interface AppComponent {
    fun getTestDao(): MovieDao
    fun getAppExecute(): AppExecutors
    fun getBaseApi(): BaseApi
    fun getFactory(): BaseViewModelFactory
    fun inject(app: MyApp)
}

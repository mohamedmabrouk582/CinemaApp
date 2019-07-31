package com.cinemaapp.app

import android.app.Activity
import android.app.Application
import androidx.fragment.app.FragmentActivity
import com.cinemaapp.di.components.AppComponent
import com.cinemaapp.di.components.DaggerAppComponent
import com.cinemaapp.di.modules.ApiModule
import com.cinemaapp.di.modules.AppModule
import com.cinemaapp.di.modules.ExecutorsModule
import com.cinemaapp.di.modules.RoomModule
import com.google.firebase.analytics.FirebaseAnalytics


/*
* Created By mabrouk on 16/03/19
* KotilnApp
*/

class MyApp : Application() {
    lateinit var appComponent: AppComponent
    override fun onCreate() {
        super.onCreate()
        FirebaseAnalytics.getInstance(this)
        appComponent = DaggerAppComponent.builder()
            .apiModule(ApiModule("https://api.themoviedb.org/3/"))
            .appModule(AppModule(this))
            .executorsModule(ExecutorsModule())
            .roomModule(RoomModule(this, "movies"))
            .build()
        appComponent.inject(this)
    }

    companion object {
        val IMG_URL="http://image.tmdb.org/t/p/w185/"
        fun get(activity: Activity): MyApp = activity.application as MyApp
        fun get(activity: FragmentActivity): MyApp = activity.application as MyApp
    }

}

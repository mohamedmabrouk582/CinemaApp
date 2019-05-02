package com.cinemaapp.di.modules

import android.content.Context
import androidx.room.Room
import com.cinemaapp.data.db.MovieDao
import com.cinemaapp.data.db.MovieDb
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


/*
* Created By mabrouk on 19/03/19
* KotilnApp
*/

@Module
class RoomModule(val app: Context, val dbName: String) {

    @Singleton
    @Provides
    fun getDao(db: MovieDb): MovieDao = db.getMovieDao()

    @Singleton
    @Provides
    fun getDb(): MovieDb = Room.databaseBuilder(app, MovieDb::class.java, dbName).fallbackToDestructiveMigration().build()

}

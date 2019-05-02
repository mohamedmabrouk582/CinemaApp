package com.cinemaapp.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.cinemaapp.data.db.converters.MovieTypeConvert
import com.cinemaapp.data.models.Movie
import com.cinemaapp.data.models.TvShow


/*
* Created By mabrouk on 20/03/19
* KotilnApp
*/
@Database(entities = arrayOf(Movie::class,TvShow::class), version = 10, exportSchema = false)
@TypeConverters(MovieTypeConvert::class)
abstract class MovieDb : RoomDatabase() {
     abstract fun getMovieDao(): MovieDao
}
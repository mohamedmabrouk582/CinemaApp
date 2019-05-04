package com.cinemaapp.data.db

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import androidx.room.Update
import com.cinemaapp.data.enums.MovieType
import com.cinemaapp.data.enums.TvShowType
import com.cinemaapp.data.models.Movie
import com.cinemaapp.data.models.MovieImage
import com.cinemaapp.data.models.TvShow


/*
* Created By mabrouk on 20/03/19
* KotilnApp
 *
*/
@Dao
interface MovieDao {
    @Insert(onConflict = REPLACE)
    fun insertMovie(movie:Movie):Long

    @Query("select * from movie where type =:type")
    fun getMovies(type: MovieType): DataSource.Factory<Int,Movie>

    @Insert(onConflict = REPLACE)
    fun insertTvShow(tvShow: TvShow) :Long

    @Query("select * from TvShow where tv_type=:type")
    fun getTvShow(type:TvShowType) : DataSource.Factory<Int,TvShow>

    @Query("select * from Movie where id =:id")
    fun getMovie(id:Long) : LiveData<Movie>

    @Query("select * from TvShow where id=:id")
    fun getTvShow(id:Long) : LiveData<TvShow>

    @Update(onConflict = REPLACE)
    fun updateMovie(movie: Movie) : Int

    @Update(onConflict = REPLACE)
    fun updateTvShow(tvShow: TvShow) :Int

}
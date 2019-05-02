package com.cinemaapp.data.models

import androidx.databinding.BaseObservable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.cinemaapp.app.MyApp
import com.cinemaapp.data.enums.TvShowType


/*
* Created By mabrouk on 06/04/19
* CinemaApp
*/
@Entity
data class TvShow(@PrimaryKey val id:Long, val original_name:String, val genre_ids:List<Int>, val name:String, val popularity:Float, val origin_country:List<String>
                  , val vote_count:Float, val first_air_date:String, val backdrop_path:String?, val original_language:String?, val vote_average:Float, val overview:String,
                  val poster_path:String?,var tv_type: TvShowType) : BaseObservable(){
    val poster_path1:String
        get() = MyApp.IMG_URL+poster_path
}
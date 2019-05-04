package com.cinemaapp.data.models

import androidx.databinding.BaseObservable
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.cinemaapp.app.MyApp
import com.cinemaapp.data.enums.TvShowType


/*
* Created By mabrouk on 06/04/19
* CinemaApp
*/
@Entity
data class TvShow(@PrimaryKey val id:Long,
                  val original_name:String,
                  val genre_ids:ArrayList<Int>?,
                  val name:String,
                  val popularity:Float,
                  val origin_country:List<String>,
                  val vote_count:Float,
                  val first_air_date:String,
                  val backdrop_path:String?,
                  val original_language:String?,
                  val vote_average:Float,
                  val overview:String,
                  val poster_path:String?,
                  val created_by:ArrayList<TvShowCreated>?,
                  val episode_run_time:ArrayList<Int>?,
                  val genres:ArrayList<Genres>?,
                  val homepage:String?,
                  val in_production:Boolean?,
                  val last_air_date:String?,
                  val last_episode_to_air:ArrayList<TvEpisode>?,
                  val next_episode_to_air:ArrayList<TvEpisode>?,
                  val networks:ArrayList<TvNetWorks>?,
                  val number_of_episodes:Int?,
                  val number_of_seasons:Int?,
                  @Embedded val production_companies:ArrayList<ProductionCompanies>?,
                  @Embedded val seasons:ArrayList<TvShowSeason>?,
                  val status:String?,
                  var tvImages:ArrayList<MovieImage>?,
                  var tvTrailer: ArrayList<MovieTrailer>?,
                  var tvCast:ArrayList<MovieCast>?,
                  var tv_type: TvShowType) : BaseObservable(){
    val poster_path1:String
        get() = MyApp.IMG_URL+poster_path
    val tvTime:String
        get() = "${episode_run_time?.get(0)?.div(60)} Hour ${episode_run_time?.get(0)?.rem(60)} Minutes"
    val tvGeners:String?
        get() {
            var content=""
            genres?.forEach {
                content+="${it.genresName} , "
            }
            return content
        }
}
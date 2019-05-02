package com.cinemaapp.data.models

import androidx.databinding.BaseObservable
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.cinemaapp.app.MyApp
import com.cinemaapp.data.enums.MovieRelatedType
import com.cinemaapp.data.enums.MovieType
import com.google.gson.annotations.SerializedName


/*
* Created By mabrouk on 06/04/19
* CinemaApp
*/
@Entity
data class Movie(@PrimaryKey val id:Long
                 , val vote_count:Int
                 , val video:Boolean
                 , val vote_average:Float
                 , val title:String
                 , val popularity:Float
                 , val release_date:String?
                 , var poster_path:String?
                 , val original_language:String
                 , val original_title:String
                 , val genre_ids:List<Int>?
                 , val backdrop_path:String?
                 , val adult:Boolean
                 , val overview:String
                 , var type: MovieType?
                 , @SerializedName("runtime") val runTime:Int?
                 , @Embedded val belongs_to_collection:BelongsCollection?
                 , val budget:Long?
                 , @Embedded val genres:ArrayList<Genres>?
                 , val imdb_id:String?
                 , @Embedded val production_companies:ArrayList<ProductionCompanies>?
                 ,val revenue:Long?
                 ,val status:String?
                 ,val tagline:String?
                 ,var movieImages:ArrayList<MovieImage>?
                 ,var movieTrailer: ArrayList<MovieTrailer>?
                 ,var movieCast:ArrayList<MovieCast>?) : BaseObservable(){
    val movieTime:String
    get() = "${runTime?.div(60)} Hour ${runTime?.rem(60)} Minutes"
    val poster_path1:String
    get() = MyApp.IMG_URL+poster_path
    val movieGeners:String?
    get() {
        var content=""
        genres?.forEach {
         content+=it.genresName
        }
        return content
    }
}
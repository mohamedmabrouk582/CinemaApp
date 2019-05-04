package com.cinemaapp.data.models

import com.cinemaapp.app.MyApp
import com.google.gson.annotations.SerializedName


/*
* Created By mabrouk on 03/05/19
* CinemaApp
*/

data class TvShowSeason(@SerializedName("id")val season_id: Long,@SerializedName("name") val season_name: String?
                        ,@SerializedName("overview") val season_overview:String?,@SerializedName("poster_path") val season_poster_path: String?
                        ,@SerializedName("air_date") val season_air_date:String?,val episode_count:Int,val season_number:Int) {
    val season_Img:String?
    get() =MyApp.IMG_URL+season_poster_path
}
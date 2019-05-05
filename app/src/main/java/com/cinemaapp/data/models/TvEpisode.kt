package com.cinemaapp.data.models

import androidx.room.PrimaryKey
import com.cinemaapp.app.MyApp
import com.google.gson.annotations.SerializedName


/*
* Created By mabrouk on 03/05/19
* CinemaApp
*/

data class TvEpisode(@PrimaryKey @SerializedName("id") val episode_id: Long, @SerializedName("name") val episode_name: String?, @SerializedName("air_date") val episode_air_date: String?
                     , val episode_number:Int, @SerializedName("overview")val episode_overview: String?, val show_id:Long, val still_path:String?, @SerializedName("vote_average")val episode_vote_average: Float
                     , @SerializedName("season_number") val episode_season_number:Int) {
    val episode_img:String?
    get() = MyApp.IMG_URL+still_path
}
package com.cinemaapp.data.models

import androidx.room.PrimaryKey
import com.cinemaapp.app.MyApp
import com.google.gson.annotations.SerializedName


/*
* Created By mabrouk on 05/05/19
* CinemaApp
*/

data class LastTvEpisode (@PrimaryKey @SerializedName("id") val episode_id_last: Long, @SerializedName("name") val episode_name_last: String?, @SerializedName("air_date") val episode_air_date_last: String?
                          , val episode_number_last:Int, @SerializedName("overview")val episode_overview_last: String?, val show_id_last:Long, val still_path_last:String?, @SerializedName("vote_average")val episode_vote_average_last: Float
                          , @SerializedName("season_number") val episode_season_numbe_lastr:Int) {
    val episode_img_last: String?
        get() = MyApp.IMG_URL + still_path_last
}
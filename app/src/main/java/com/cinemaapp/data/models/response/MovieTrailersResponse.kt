package com.cinemaapp.data.models.response

import com.cinemaapp.data.models.MovieTrailer
import com.google.gson.annotations.SerializedName


/*
* Created By mabrouk on 20/04/19
* CinemaApp
*/

data class MovieTrailersResponse(@SerializedName("id")val trailers_id: Long, @SerializedName("results") val trailers_results:ArrayList<MovieTrailer>)
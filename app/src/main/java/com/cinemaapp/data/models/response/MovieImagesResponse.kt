package com.cinemaapp.data.models.response

import com.cinemaapp.data.models.MovieImage
import com.google.gson.annotations.SerializedName


/*
* Created By mabrouk on 15/04/19
* CinemaApp
*/

data class MovieImagesResponse(@SerializedName("id") val images_id:Long,val backdrops:ArrayList<MovieImage>?,val posters:ArrayList<MovieImage>?)
package com.cinemaapp.data.models

import androidx.databinding.BaseObservable
import com.google.gson.annotations.SerializedName


/*
* Created By mabrouk on 15/04/19
* CinemaApp
*/

data class Genres(@SerializedName("id")val genresId: Long,@SerializedName("name") val genresName:String) : BaseObservable()
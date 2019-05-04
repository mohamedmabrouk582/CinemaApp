package com.cinemaapp.data.models

import androidx.databinding.BaseObservable
import com.google.gson.annotations.SerializedName


/*
* Created By mabrouk on 15/04/19
* CinemaApp
*/

data class BelongsCollection(@SerializedName("id")val belongsId:Long, @SerializedName("Name")val belongsName:String?,
                             @SerializedName("poster_path")val  belongsPoster: String?) : BaseObservable()
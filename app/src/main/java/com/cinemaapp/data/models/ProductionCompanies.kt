package com.cinemaapp.data.models

import androidx.databinding.BaseObservable
import com.cinemaapp.app.MyApp
import com.google.gson.annotations.SerializedName


/*
* Created By mabrouk on 15/04/19
* CinemaApp
*/

data class ProductionCompanies(@SerializedName("id")val companyId:Long,@SerializedName("name") val companyName:String,
                               val logo_path:String?,val origin_country:String?): BaseObservable(){
      val poster:String
      get() = MyApp.IMG_URL+logo_path
}
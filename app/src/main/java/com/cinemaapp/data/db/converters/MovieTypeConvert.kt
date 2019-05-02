package com.cinemaapp.data.db.converters

import androidx.room.TypeConverter
import com.cinemaapp.data.enums.MovieType
import com.cinemaapp.data.enums.TvShowType
import com.cinemaapp.data.models.*
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type
import java.util.*
import kotlin.collections.ArrayList


/*
* Created By mabrouk on 06/04/19
* CinemaApp
*/

class MovieTypeConvert {
    @TypeConverter
    fun ConvertEnumToString(data: MovieType?) :String = Gson().toJson(data)

    @TypeConverter
    fun ConvertStringToEnum(data:String) : MovieType? {
        val type= object : TypeToken<MovieType>(){}.type
        return Gson().fromJson(data,type)
    }

    @TypeConverter
    fun ConvertTvTypeToJson(data:TvShowType?) : String =Gson().toJson(data)

    @TypeConverter
    fun ConvertJsonToTvShow(data: String) : TvShowType? {
        val type=object : TypeToken<TvShowType>(){}.type
        return Gson().fromJson(data,type)
    }

    @TypeConverter
    fun ConvertIntegerToString(data:List<Int>?) : String = Gson().toJson(data)

    @TypeConverter
    fun ConvertStringToList(data:String) : List<Int>?{
        val type= object :TypeToken<List<Int>>(){}.type
        return Gson().fromJson(data,type)
    }

    @TypeConverter
    fun ConvertListToString(data:List<String>?) : String = Gson().toJson(data)

    @TypeConverter
    fun ConvertJsonToList(data:String) :List<String>? {
        val type=object : TypeToken<List<String>>(){}.type
        return Gson().fromJson(data,type)
    }

    @TypeConverter
    fun convertImagesToString(data:ArrayList<MovieImage>?): String =Gson().toJson(data)

    @TypeConverter
    fun convertToMovieImage(data:String): ArrayList<MovieImage>?{
        val type=object : TypeToken<ArrayList<MovieImage>>(){}.type
        return Gson().fromJson(data,type)
    }

    @TypeConverter
    fun convertTrailerToString(data:ArrayList<MovieTrailer>?) : String =Gson().toJson(data)

    @TypeConverter
    fun convertToTrailer(data:String) : ArrayList<MovieTrailer>? {
        val type=object : TypeToken<ArrayList<MovieTrailer>?>(){}.type
        return Gson().fromJson(data,type)
    }

    @TypeConverter
    fun convertCastToString(data:ArrayList<MovieCast>?) :String = Gson().toJson(data)

    @TypeConverter
    fun convertToCast(data:String): ArrayList<MovieCast>?{
        val type=object : TypeToken<ArrayList<MovieCast>>(){}.type
        return Gson().fromJson(data,type)
    }

    @TypeConverter
    fun convertGenersToString(data:ArrayList<Genres>?) : String = Gson().toJson(data)

    @TypeConverter
    fun convertToGeners(data:String) : ArrayList<Genres>? {
        val type=object : TypeToken<ArrayList<Genres>>(){}.type
        return Gson().fromJson(data,type)
    }

}
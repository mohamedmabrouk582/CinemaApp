package com.cinemaapp.data.models.response

import com.cinemaapp.data.models.MovieCast


/*
* Created By mabrouk on 20/04/19
* CinemaApp
*/

data class MovieCastResponse(val id:Long,val cast:ArrayList<MovieCast>)
package com.cinemaapp.data.models.response

import com.cinemaapp.data.models.Movie


/*
* Created By mabrouk on 06/04/19
* CinemaApp
*/
data class MovieResponse(val results:List<Movie>, val total_pages:Int, val total_results:Int, val page:Int)
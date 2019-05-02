package com.cinemaapp.data.models.response

import com.cinemaapp.data.models.TvShow


/*
* Created By mabrouk on 06/04/19
* CinemaApp
*/

data class TvShowResponse(val results:List<TvShow>, val total_pages:Int, val total_results:Int, val page:Int)
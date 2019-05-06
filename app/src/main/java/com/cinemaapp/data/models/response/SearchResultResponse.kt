package com.cinemaapp.data.models.response

import com.cinemaapp.data.models.SearchResult


/*
* Created By mabrouk on 06/05/19
* CinemaApp
*/

data class SearchResultResponse (val results:ArrayList<SearchResult>, val total_pages:Int, val total_results:Int, val page:Int)
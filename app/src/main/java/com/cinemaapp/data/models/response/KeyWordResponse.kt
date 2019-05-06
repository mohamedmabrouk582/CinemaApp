package com.cinemaapp.data.models.response

import com.cinemaapp.data.models.KeyWord


/*
* Created By mabrouk on 06/05/19
* CinemaApp
*/

data class KeyWordResponse (val results:ArrayList<KeyWord>, val total_pages:Int, val total_results:Int, val page:Int)
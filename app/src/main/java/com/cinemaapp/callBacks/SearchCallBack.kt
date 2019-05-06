package com.cinemaapp.callBacks

import androidx.paging.PagedList
import com.cinemaapp.data.models.SearchResult
import com.cinemaapp.ui.activities.SearchActivity


/*
* Created By mabrouk on 06/05/19
* CinemaApp
*/

interface SearchCallBack : BaseCallBack{
    fun getSearchActivity():SearchActivity
    fun loadKeyWorkds(data : ArrayList<String>)
    fun loadMoreKeyWords(data:ArrayList<String>)
    fun loadSearchData(data: PagedList<SearchResult>)
}
package com.cinemaapp.viewModels.base

import com.cinemaapp.callBacks.BaseCallBack


/*
* Created By mabrouk on 16/03/19
* KotilnApp
*/

interface BaseVmodel<v : BaseCallBack> {
    fun attachView(view: v)
}

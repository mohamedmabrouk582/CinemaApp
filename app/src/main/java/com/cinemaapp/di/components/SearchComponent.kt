package com.cinemaapp.di.components

import com.cinemaapp.di.scopes.SearchScope
import com.cinemaapp.ui.activities.SearchActivity
import dagger.Component


/*
* Created By mabrouk on 06/05/19
* CinemaApp
*/
@SearchScope
@Component(dependencies = [AppComponent::class])
interface SearchComponent {
    fun inject(searchActivity: SearchActivity)
}
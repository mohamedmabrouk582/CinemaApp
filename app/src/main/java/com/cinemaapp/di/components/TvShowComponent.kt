package com.cinemaapp.di.components

import com.cinemaapp.di.scopes.TvShowDetailsScope
import com.cinemaapp.ui.fragments.TvShowDetailsFragment
import dagger.Component


/*
* Created By mabrouk on 04/05/19
* CinemaApp
*/
@TvShowDetailsScope
@Component(dependencies = [AppComponent::class])
interface TvShowComponent {
    fun inject(fragment: TvShowDetailsFragment)
}
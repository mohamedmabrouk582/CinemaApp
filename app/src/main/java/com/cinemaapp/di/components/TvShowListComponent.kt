package com.cinemaapp.di.components

import com.cinemaapp.di.scopes.TvShowListScope
import com.cinemaapp.ui.fragments.TvShowListFragment
import dagger.Component


/*
* Created By mabrouk on 06/04/19
* CinemaApp
*/
@TvShowListScope
@Component(dependencies = arrayOf(AppComponent::class))
interface TvShowListComponent {
    fun inject(fragment: TvShowListFragment)
}
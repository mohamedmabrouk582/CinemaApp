package com.cinemaapp.di.components

import com.cinemaapp.di.scopes.MovieListScope
import com.cinemaapp.ui.fragments.MovieListFragment
import dagger.Component


/*
* Created By mabrouk on 06/04/19
* CinemaApp
*/
@MovieListScope
@Component(dependencies = arrayOf(AppComponent::class))
interface MovieListComponent {
    fun inject(fragment: MovieListFragment)
}
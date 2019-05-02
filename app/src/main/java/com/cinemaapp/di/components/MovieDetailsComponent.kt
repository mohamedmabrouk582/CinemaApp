package com.cinemaapp.di.components

import com.cinemaapp.di.scopes.MovieDetailsScope
import com.cinemaapp.ui.fragments.MovieDetailsFragment
import dagger.Component


/*
* Created By mabrouk on 20/04/19
* CinemaApp
*/
@MovieDetailsScope
@Component(dependencies = arrayOf(AppComponent::class))
interface MovieDetailsComponent {
    fun inject(fragment: MovieDetailsFragment)
}
package com.cinemaapp.data.api

import com.cinemaapp.data.enums.MovieRelatedType
import com.cinemaapp.data.enums.MovieType
import com.cinemaapp.data.enums.TvShowType
import com.cinemaapp.data.models.Movie
import com.cinemaapp.data.models.response.*
import io.reactivex.Observable
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


/*
* Created By mabrouk on 16/03/19
* KotilnApp
*/

interface BaseApi {
    @GET("movie/{type}?api_key=c258ef3167d2f4ec83da643c7f76b785")
    fun getMovies(@Path("type") type: MovieType, @Query("page") page:Int):Observable<MovieResponse>

    @GET("tv/{tv_type}?api_key=c258ef3167d2f4ec83da643c7f76b785")
    fun getTvShow(@Path("tv_type") tv_type:TvShowType,@Query("page") page:Int) : Observable<TvShowResponse>

    @GET("movie/{movie_id}?api_key=c258ef3167d2f4ec83da643c7f76b785")
    fun getMovieDetails(@Path("movie_id") movie_id:Long) : Observable<Movie>

    @GET("movie/{movie_id}/{type}?api_key=c258ef3167d2f4ec83da643c7f76b785")
    fun getRelatedMovies(@Path("movie_id") movie_id:Long,@Path("type") type:MovieRelatedType,@Query("page") page:Int) :Observable<MovieResponse>

    @GET("movie/{movie_id}/images?api_key=c258ef3167d2f4ec83da643c7f76b785")
    fun getMovieImages(@Path("movie_id") movie_id:Long) : Observable<MovieImagesResponse>

    @GET("movie/{movie_id}/videos?api_key=c258ef3167d2f4ec83da643c7f76b785")
    fun getMovieTrailers(@Path("movie_id") movie_id:Long) : Observable<MovieTrailersResponse>

    @GET("movie/{movie_id}/credits?api_key=c258ef3167d2f4ec83da643c7f76b785")
    fun getMovieCast(@Path("movie_id") movie_id:Long) :Observable<MovieCastResponse>

    @GET("movie/{movie_id}/credits?api_key=c258ef3167d2f4ec83da643c7f76b785")
    fun getMovieCasts(@Path("movie_id") movie_id:Long) :Deferred<MovieCastResponse>

}
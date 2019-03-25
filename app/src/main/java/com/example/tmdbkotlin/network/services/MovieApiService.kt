package com.example.tmdbkotlin.network.services


import com.example.tmdbkotlin.tmdb.model.MovieResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApiService {
    @GET("movie/top_rated")
    fun getTopRatedMovies(@Query("api_key") apiKey: String): Call<MovieResponse>

    @GET("trending/{media_type}/{time_window}")
    fun getTrendingMovies(@Path("media_type") mediaType: String, @Path("time_window") timeWindow: String, @Query("api_key") apiKey: String): Call<MovieResponse>

    @GET("search/movie")
    fun getMovies(@Query("api_key") apiKey: String, @Query("query") query: String): Call<MovieResponse>
}

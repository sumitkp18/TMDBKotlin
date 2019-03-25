package com.example.tmdbkotlin.tmdb.view

import com.example.tmdbkotlin.tmdb.model.Movie

internal interface IMovieSearchView {
    fun onTopRatedMovieSearchSuccess(movies: List<Movie>)

    fun onTopRatedMovieSearchFailure(errorResponse: String)

    fun onTrendingMovieSearchSuccess(movies: List<Movie>)

    fun onTrendingMovieSearchFailure(errorResponse: String)

    fun onMovieSearchSuccess(movies: List<Movie>)

    fun onMovieSearchFailure(errorResponse: String)
}

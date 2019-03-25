package com.example.tmdbkotlin.tmdb.Intractor

import android.util.Log
import com.example.tmdbkotlin.network.RetrofitClient
import com.example.tmdbkotlin.network.services.MovieApiService
import com.example.tmdbkotlin.tmdb.model.MovieResponse
import com.example.tmdbkotlin.tmdb.view.MovieSearchView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class MovieSearch(val movieSearchView: MovieSearchView) {

    /**
     * getMovies method fetches movie information of various movies from TMDB
     * whose name matches with the search query.
     * On successful retrieval of data from the api it sends it to the MovieSearchView
     * to display as suggestions for the AutoCompleteTextView.
     *
     * @property getMovies
     * This parameter is the movie name which is used as a search query in the api.
     *
     */
    fun getMovies(movieName: String) {
        tmdbClient = RetrofitClient().getRetrofitClient(BASE_URL)
        val movieApiService = tmdbClient!!.create(MovieApiService::class.java)

        val movieNameHTML = movieName.replace("\\s+".toRegex(), "+")

        Log.d(TAG, "movie name: $movieName")

        val call = movieApiService.getMovies(API_KEY, movieNameHTML)

        call.enqueue(object : Callback<MovieResponse> {
            override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {
                if (response.body() != null) {
                    val movies = response.body()!!.results

                    Log.d(TAG, "Number of movies received: " + movies?.size)

                        movieSearchView.onMovieSearchSuccess(movies!!)
                }
            }
            override fun onFailure(call: Call<MovieResponse>, throwable: Throwable) {

                movieSearchView.onMovieSearchFailure("Network Error")

                Log.e(TAG, throwable.toString())
            }
        })
    }

    /**
     * getTopRatedMovies method fetches information from TMDB of the current
     * top rated movies.
     * It sends the fetched list of data to the MovieSearchView for displaying
     * the content in a recycler view.
     */
    fun getTopRatedMovies() {

        tmdbClient = RetrofitClient().getRetrofitClient(BASE_URL)
        val movieApiService = tmdbClient!!.create(MovieApiService::class.java)

        val call = movieApiService.getTopRatedMovies(API_KEY)

        call.enqueue(object : Callback<MovieResponse> {
            override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {
                val movies = response.body()!!.results

                Log.d(TAG, "Number of movies received: " + movies?.size)

                movieSearchView.onTopRatedMovieSearchSuccess(movies!!)


            }

            override fun onFailure(call: Call<MovieResponse>, throwable: Throwable) {

                movieSearchView.onTopRatedMovieSearchFailure("Network Error")

                Log.e(TAG, throwable.toString())
            }
        })
    }

    /**
     * getTrendingMovies method fetches information from TMDB of the movies
     * which are trending in the given time window.
     * It sends the fetched list of data to the MovieSearchView for displaying
     * the content in a recycler view.
     *
     * @property mediaType
     * This parameter specifies the the content or type of the media : movie/series/.. etc.
     *
     * @property timeWindow
     * This parameter specifies the time window of the trend: day/week/.. etc.
     */
    fun getTrendingMovies(mediaType: String, timeWindow: String) {

        tmdbClient = RetrofitClient().getRetrofitClient(BASE_URL)
        val movieApiService = tmdbClient!!.create(MovieApiService::class.java)

        val call = movieApiService.getTrendingMovies(mediaType, timeWindow, API_KEY)

        call.enqueue(object : Callback<MovieResponse> {
            override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {
                val movies = response.body()!!.results

                Log.d(TAG, "Number of movies received: " + movies?.size)

                movieSearchView.onTrendingMovieSearchSuccess(movies!!)


            }

            override fun onFailure(call: Call<MovieResponse>, throwable: Throwable) {

                movieSearchView.onTrendingMovieSearchFailure("Network Error")

                Log.e(TAG, throwable.toString())
            }
        })
    }

    companion object {
        private val TAG = MovieSearch::class.java.simpleName
        private val BASE_URL = "http://api.themoviedb.org/3/"
        private val API_KEY = "70209f5734f655399f99a0fab66d6e9b"
        private var tmdbClient: Retrofit? = null
    }
}

package com.example.tmdbkotlin.tmdb.model

import com.google.gson.annotations.SerializedName

@Suppress("unused")
data class MovieResponse(@SerializedName("page")
                         var page: Int = 0,
                         @SerializedName("results")
                         var results: List<Movie>? = null,
                         @SerializedName("total_results")
                         var totalResults: Int = 0,
                         @SerializedName("total_pages")
                         var totalPages: Int = 0)

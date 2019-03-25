package com.example.tmdbkotlin.tmdb.model

data class MovieSuggestion(val movieName: String?,
                           val releaseDate: String?,
                           val description: String?,
                           val rating: Double?,
                           val poster: String?) {

    override fun toString(): String {
        return movieName!!
    }
}

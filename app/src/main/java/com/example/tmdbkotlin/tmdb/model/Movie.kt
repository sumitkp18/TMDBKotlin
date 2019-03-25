package com.example.tmdbkotlin.tmdb.model

import com.google.gson.annotations.SerializedName

import java.util.ArrayList

data class Movie(
    @field:SerializedName("poster_path")
    val posterPath: String?,
    @field:SerializedName("adult")
    val isAdult: Boolean,
    @field:SerializedName("overview")
    val overview: String?,
    @field:SerializedName("release_date")
    val releaseDate: String?,
    @SerializedName("genre_ids")
    val genreIds: List<Int>,
    @field:SerializedName("id")
    val id: Int?,
    @field:SerializedName("original_title")
    val originalTitle: String?,
    @field:SerializedName("original_language")
    val originalLanguage: String?,
    @field:SerializedName("title")
    val title: String?,
    @field:SerializedName("backdrop_path")
    val backdropPath: String?,
    @field:SerializedName("popularity")
    val popularity: Double?,
    @field:SerializedName("vote_count")
    val voteCount: Int?,
    @field:SerializedName("video")
    val video: Boolean?,
    @field:SerializedName("vote_average")
    val voteAverage: Double?
)
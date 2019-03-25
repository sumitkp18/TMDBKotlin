package com.example.tmdbkotlin.tmdb.adapters

import android.app.Activity
import android.content.Context
import android.support.v7.widget.AppCompatTextView
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.example.tmdbkotlin.R
import com.example.tmdbkotlin.tmdb.model.MovieSuggestion
import com.squareup.picasso.Picasso

class MovieSuggestAdapter(context: Context, private val resourceId: Int) :
    ArrayAdapter<MovieSuggestion>(context, resourceId) {

    private val IMAGE_URL_BASE_PATH = "http://image.tmdb.org/t/p/w92//"
    private var movieSuggestions: List<MovieSuggestion>? = null

    fun setMovieSuggestions(movieSuggestions: List<MovieSuggestion>) {
        this.movieSuggestions = movieSuggestions
    }

    override fun getCount(): Int {
        return movieSuggestions!!.size
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var convertView = convertView

        if (convertView == null) {
            val inflater = (context as Activity).layoutInflater
            convertView = inflater.inflate(resourceId, parent, false)
        }
        val movieSuggestion = getItem(position)

        val movieName = convertView!!.findViewById<AppCompatTextView>(R.id.movie_title)
        assert(movieSuggestion != null)
        movieName.text = movieSuggestion!!.movieName

        val releaseDate = convertView.findViewById<AppCompatTextView>(R.id.release_date)
        releaseDate.text = movieSuggestion.releaseDate

        val movieRating = convertView.findViewById<RatingBar>(R.id.movie_rating)
        movieRating.rating = movieSuggestion.rating!!.toFloat() / 2

        val movieImage = convertView.findViewById<ImageView>(R.id.movie_image)

        val imageUrl = IMAGE_URL_BASE_PATH + movieSuggestion!!.poster

        Picasso.with(context)
            .load(imageUrl)
            .placeholder(android.R.drawable.ic_media_play)
            .error(android.R.drawable.ic_media_play)
            .into(movieImage)


        return convertView
    }

    override fun getItem(position: Int): MovieSuggestion? {
        return movieSuggestions!![position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }


}

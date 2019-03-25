package com.example.tmdbkotlin.tmdb.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RatingBar
import android.widget.TextView
import com.example.tmdbkotlin.R
import com.example.tmdbkotlin.tmdb.model.Movie
import com.squareup.picasso.Picasso

class MoviesAdapter(private val movies: List<Movie>, private val rowLayout: Int,  val context: Context) :
    RecyclerView.Adapter<MoviesAdapter.MovieViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, position: Int): MoviesAdapter.MovieViewHolder {

        val view = LayoutInflater.from(viewGroup.context).inflate(rowLayout, viewGroup, false)
        return MovieViewHolder(view)
    }

    override fun onBindViewHolder(movieViewHolder: MoviesAdapter.MovieViewHolder, position: Int) {

        val imageUrl = IMAGE_URL_BASE_PATH + movies[position].posterPath
        Picasso.with(context)
            .load(imageUrl)
            .placeholder(android.R.drawable.ic_media_play)
            .error(android.R.drawable.ic_media_play)
            .into(movieViewHolder.movieImage)

        movieViewHolder.movieTitle.text = movies[position].title
        movieViewHolder.releaseDate.text = movies[position].releaseDate
        movieViewHolder.rating.rating = (movies[position].voteAverage!! / 2).toFloat()

        Log.d(TAG, "Title: " + movies[position].title)
        Log.d(TAG, "Rating: " + movies[position].voteAverage)

    }

    override fun getItemCount(): Int {
        return movies.size
    }

    class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var moviesRow: LinearLayout
        var movieTitle: TextView
        var rating: RatingBar
        var releaseDate: TextView
        var movieImage: ImageView

        init {

            moviesRow = itemView.findViewById(R.id.movie_row)
            movieTitle = itemView.findViewById(R.id.movie_title)
            movieImage = itemView.findViewById(R.id.movie_image)
            releaseDate = itemView.findViewById(R.id.release_date)
            rating = itemView.findViewById(R.id.movie_rating)
        }

    }

    companion object {

        private val TAG = MoviesAdapter::class.java.simpleName
        private const val IMAGE_URL_BASE_PATH = "http://image.tmdb.org/t/p/w185//"
    }
}

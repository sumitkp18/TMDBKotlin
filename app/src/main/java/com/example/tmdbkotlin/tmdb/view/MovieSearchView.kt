package com.example.tmdbkotlin.tmdb.view

import android.content.Context
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.*
import com.example.tmdbkotlin.R
import com.example.tmdbkotlin.tmdb.Intractor.MovieSearch
import com.example.tmdbkotlin.tmdb.adapters.MovieSuggestAdapter
import com.example.tmdbkotlin.tmdb.adapters.MoviesAdapter
import com.example.tmdbkotlin.tmdb.model.Movie
import com.example.tmdbkotlin.tmdb.model.MovieSuggestion
import com.squareup.picasso.Picasso

import java.util.ArrayList
import android.content.Intent



class MovieSearchView : AppCompatActivity(), IMovieSearchView {

    private var topRatedMoviesRecyclerView: RecyclerView? = null
    private var trendingMoviesRecyclerView: RecyclerView? = null

    private var mAutoText: AutoCompleteTextView? = null
    private var adapter: MovieSuggestAdapter? = null

    private var doubleBackToExitPressedOnce = false



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_search)

    }

    override fun onResume() {
        super.onResume()

        topRatedMoviesRecyclerView = findViewById(R.id.top_rated_movies_RecyclerView)
        topRatedMoviesRecyclerView!!.setHasFixedSize(true)
        val topRatedMoviesLayoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        topRatedMoviesRecyclerView!!.layoutManager = topRatedMoviesLayoutManager


        trendingMoviesRecyclerView = findViewById(R.id.trending_movies_RecyclerView)
        trendingMoviesRecyclerView!!.setHasFixedSize(true)
        val trendingMoviesLayoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        trendingMoviesRecyclerView!!.layoutManager = trendingMoviesLayoutManager


        //movie search suggestions
        mAutoText = findViewById(R.id.movie_search_atv)
        adapter = MovieSuggestAdapter(this, R.layout.list_item_movie)
        mAutoText!!.setAdapter<MovieSuggestAdapter>(adapter)
        mAutoText!!.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {

                if (mAutoText!!.threshold >= 3) {
                    MovieSearch(this@MovieSearchView).getMovies(s.toString())
                }
            }

            override fun afterTextChanged(s: Editable) {}
        })

        mAutoText!!.onItemClickListener = getOnMovieItemClickListener()

        loadDefaultContent()

    }

    fun getOnMovieItemClickListener() = AdapterView.OnItemClickListener{parent, view, position, id ->
        hideKeyboard()
        findViewById<LinearLayout>(R.id.movie_desc_layout).visibility = View.VISIBLE

        val movieDescImage = findViewById<ImageView>(R.id.movie_desc_image)
        val movieDescTitle = findViewById<TextView>(R.id.movie_desc_title)
        val movieDescRelease = findViewById<TextView>(R.id.movie_desc_release_date)
        val movieDescRating = findViewById<RatingBar>(R.id.movie_desc_rating)
        val movieDesc = findViewById<TextView>(R.id.movie_desc)

        val movieSuggestion = parent.getItemAtPosition(position) as MovieSuggestion

        val image_url = IMAGE_URL_BASE_PATH + movieSuggestion.poster

        Picasso.with(view.context)
            .load(image_url)
            .placeholder(android.R.drawable.ic_media_play)
            .error(android.R.drawable.ic_media_play)
            .into(movieDescImage)

        movieDescTitle.text = movieSuggestion.movieName
        movieDescRating.rating = (movieSuggestion.rating!! / 2).toFloat()
        movieDescRelease.text = movieSuggestion.releaseDate
        movieDesc.text = movieSuggestion.description
    }

    fun getOnItemClickListener() = AdapterView.OnItemClickListener { parent, view, position, id ->
        hideKeyboard()
        findViewById<LinearLayout>(R.id.movie_desc_layout).visibility = View.VISIBLE

        val movieDescImage = findViewById<ImageView>(R.id.movie_desc_image)
        val movieDescTitle = findViewById<TextView>(R.id.movie_desc_title)
        val movieDescRelease = findViewById<TextView>(R.id.movie_desc_release_date)
        val movieDescRating = findViewById<RatingBar>(R.id.movie_desc_rating)
        val movieDesc = findViewById<TextView>(R.id.movie_desc)

        val movieSuggestion = parent.getItemAtPosition(position) as MovieSuggestion

        val image_url = IMAGE_URL_BASE_PATH + movieSuggestion.poster

        Picasso.with(view.context)
            .load(image_url)
            .placeholder(android.R.drawable.ic_media_play)
            .error(android.R.drawable.ic_media_play)
            .into(movieDescImage)

        movieDescTitle.text = movieSuggestion.movieName
        movieDescRating.rating = (movieSuggestion.rating!! / 2).toFloat()
        movieDescRelease.text = movieSuggestion.releaseDate
        movieDesc.text = movieSuggestion.description
    }

    fun loadDefaultContent(){

        //Load top rated movies
        MovieSearch(this).getTopRatedMovies()

        //Load trending movies of the week
        MovieSearch(this).getTrendingMovies("movie", "week")

    }

    /**
     * hideKeyboard method is used to hide the keyboard from the current activity
     */
    fun hideKeyboard() {
        val view = this.currentFocus
        if (view != null) {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    /**
     * onBackPressed method is used to handle the
     * action on pressing the back button on the phone.
     *
     * It checks the state of the variable "doubleBackToExitPressedOnce"
     * and creates a toast suggesting to press the back button twice to close
     * the application.
     */
    override fun onBackPressed() {
        if(doubleBackToExitPressedOnce){
            val intent = Intent(Intent.ACTION_MAIN)
            intent.addCategory(Intent.CATEGORY_HOME)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)

            finish()

        }
        else {
            this.doubleBackToExitPressedOnce = true
            Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show()
        }
    }

    /**
     * onTopRatedMovieSearchSuccess is a callback method.
     * It is called from the MovieSearch class after response from
     * TMDB api call for a successful top rated movie search.
     *
     * It creates and adapter using the List object "movies" and
     * sets it to the recycler view.
     *
     * @property movies: List<Movie>
     *     This parameter contains the list of movies returned from
     *     TMDB api call for movie search.
     *
     */
    override fun onTopRatedMovieSearchSuccess(movies: List<Movie>) {

        val topRatedMoviesAdapter = MoviesAdapter(
            movies, R.layout.movie_tile,
            this
        )
        topRatedMoviesRecyclerView!!.adapter = topRatedMoviesAdapter

    }

    /**
     * onTopRatedMovieSearchFailure is a callback method.
     * It is called from the MovieSearch class after response from
     * TMDB api call for failure in top rated movie search.
     *
     * @property errorResponse: String
     * This parameter contains the error message to be displayed to the user.
     */
    override fun onTopRatedMovieSearchFailure(errorResponse: String) {

        val parent = findViewById<View>(R.id.movie_search_layout)
        Snackbar.make(parent, errorResponse, Snackbar.LENGTH_LONG)
            .setAction("Action", null).show()

    }

    /**
     * onTrendingMovieSearchSuccess is a callback method.
     * It is called from the MovieSearch class after response from
     * TMDB api call for a successful trending movie search.
     *
     * It creates and adapter using the List object "movies" and
     * sets it to the recycler view.
     *
     * @property movies: List<Movie>
     *     This parameter contains the list of movies returned from
     *     TMDB api call for movie search.
     *
     */
    override fun onTrendingMovieSearchSuccess(movies: List<Movie>) {

        val trendingMoviesAdapter = MoviesAdapter(
            movies, R.layout.movie_tile,
            this
        )
        trendingMoviesRecyclerView!!.adapter = trendingMoviesAdapter

    }

    /**
     * onTrendingMovieSearchFailure is a callback method.
     * It is called from the MovieSearch class after response from
     * TMDB api call for failure in trending movie search.
     *
     * @property errorResponse: String
     * This parameter contains the error message to be displayed to the user.
     */
    override fun onTrendingMovieSearchFailure(errorResponse: String) {

        val parent = findViewById<View>(R.id.movie_search_layout)
        Snackbar.make(parent, errorResponse, Snackbar.LENGTH_LONG)
            .setAction("Action", null).show()

    }

    /**
     * onMovieSearchSuccess is a callback method.
     * It is called from the MovieSearch class after response from
     * TMDB api call for a successful movie search.
     *
     * It fills the ArrayList object "movieSuggestions" with the data from the
     * List object "movies" received from api response and sets the adapter
     * for the AutoCompleteTextView.
     *
     *
     * @property movies: List<Movie>
     *     This parameter contains the list of movies returned from
     *     TMDB api call for movie search.
     *
     */
    override fun onMovieSearchSuccess(movies: List<Movie>) {

        val movieSuggestions = ArrayList<MovieSuggestion>()

        for (movie in movies) {

            val title = movie.title
            val releaseDate = movie.releaseDate
            val rating = movie.voteAverage
            val description = movie.overview
            val posterPath = movie.posterPath

            movieSuggestions.add(
                MovieSuggestion(title, releaseDate, description, rating, posterPath)
            )
        }

        adapter!!.setMovieSuggestions(movieSuggestions)
        adapter!!.notifyDataSetChanged()

    }

    /**
     * onMovieSearchFailure is a callback method.
     * It is called from the MovieSearch class after response from
     * TMDB api call for failure in movie search.
     *
     * @property errorResponse: String
     * This parameter contains the error message to be displayed to the user.
     */
    override fun onMovieSearchFailure(errorResponse: String) {

        val parent = findViewById<View>(R.id.movie_search_layout)
        Snackbar.make(parent, errorResponse, Snackbar.LENGTH_LONG)
            .setAction("Action", null).show()

    }

    companion object {

        const val IMAGE_URL_BASE_PATH = "http://image.tmdb.org/t/p/w500//"
    }


}

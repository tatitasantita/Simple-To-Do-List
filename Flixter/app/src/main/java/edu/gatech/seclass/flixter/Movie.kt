package edu.gatech.seclass.flixter

import org.json.JSONArray

data class Movie(
    val movieId: Int,
    private val posterPath: String,
    val title: String,
    val overview: String,
){
    val posterImageUrl= "https://image.tmdb.org/t/p/w342/$posterPath"
    companion object{
        fun fromJsonArray(movieJsonArray: JSONArray): List<Movie> {
            val movies= mutableListOf<Movie>()
            for(i in 0 until movieJsonArray.length()){
                val movieJSON = movieJsonArray.getJSONObject(i)
                movies.add(
                    Movie(
                        movieJSON.getInt("id"),
                        movieJSON.getString("poster_path"),
                        movieJSON.getString("title"),
                        movieJSON.getString("overview")
                    )
                )
            }
            return movies
        }
    }
}
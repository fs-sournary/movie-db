package framgia.com.moviedbkotlin.api

import com.google.gson.annotations.SerializedName
import framgia.com.moviedbkotlin.data.Cast
import framgia.com.moviedbkotlin.data.Movie
import framgia.com.moviedbkotlin.data.Video
import io.reactivex.Single
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Created: 11/07/2018
 * By: Sang
 * Description:
 */
private const val PARAM_GENRE_ID = "with_genres"
private const val PARAM_MOVIE_ID = "movie_id"
private const val PARAM_PAGE = "page"

interface MovieApi {

    @GET("movie/now_playing")
    fun getNowPlayingMovies(@Query(PARAM_PAGE) page: Int): Single<ListResponse<Movie>>

    @GET("movie/popular")
    // fun getPopularMovies(@Query(PARAM_PAGE) page: Int): Single<ListResponse<Movie>>
    fun getPopularMovies(@Query(PARAM_PAGE) page: Int): Call<ListResponse<Movie>>

    @GET("movie/top_rated")
    fun getTopRateMovies(@Query(PARAM_PAGE) page: Int): Call<ListResponse<Movie>>

    @GET("movie/upcoming")
    fun getUpcomingMovies(@Query(PARAM_PAGE) page: Int): Single<ListResponse<Movie>>

    @GET("movie/{movie_id}/credits")
    fun getCredits(@Path("movie_id") movieId: Int): Single<CreditResponse>

    @GET("discover/movie")
    fun getMoviesByGenre(
        @Query(PARAM_GENRE_ID) genreId: String,
        @Query(PARAM_PAGE) page: Int
    ): Single<ListResponse<Movie>>

    @GET("movie/{$PARAM_MOVIE_ID}/videos")
    fun getVideos(@Path(PARAM_MOVIE_ID) movieId: Int): Single<VideoResponse>
}


class CreditResponse(
    @SerializedName("id")
    val id: Int,
    @SerializedName("cast")
    val casts: List<Cast>? = null
)


class VideoResponse(
    var id: Int = 0,
    var results: List<Video>? = null
)

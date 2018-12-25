package framgia.com.moviedbkotlin.api

import com.google.gson.annotations.SerializedName
import framgia.com.moviedbkotlin.data.Cast
import framgia.com.moviedbkotlin.data.Movie
import framgia.com.moviedbkotlin.data.Video
import framgia.com.moviedbkotlin.repository.Constants
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


interface MovieApi {

    @GET("movie/now_playing")
    fun getNowPlayingMovies(@Query(Constants.PARAM_PAGE) page: Int): Single<ListResponse<Movie>>

    @GET("movie/popular")
    // fun getPopularMovies(@Query(PARAM_PAGE) page: Int): Single<ListResponse<Movie>>
    fun getPopularMovies(@Query(Constants.PARAM_PAGE) page: Int): Single<ListResponse<Movie>>

    @GET("movie/top_rated")
    fun getTopRateMovies(@Query(Constants.PARAM_PAGE) page: Int): Single<ListResponse<Movie>>

    @GET("movie/upcoming")
    fun getUpcomingMovies(@Query(Constants.PARAM_PAGE) page: Int): Single<ListResponse<Movie>>

    @GET("movie/{movie_id}/credits")
    fun getCredits(@Path("movie_id") movieId: Int): Single<CreditResponse>

    @GET("discover/movie")
    fun getMoviesByGenre(
        @Query(Constants.PARAM_GENRE_ID) genreId: String,
        @Query(Constants.PARAM_PAGE) page: Int
    ): Single<ListResponse<Movie>>

    @GET("movie/{${Constants.PARAM_MOVIE_ID}/videos")
    fun getVideos(@Path(Constants.PARAM_MOVIE_ID) movieId: Int): Single<VideoResponse>
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

package framgia.com.moviedbkotlin.api

import com.google.gson.annotations.SerializedName
import framgia.com.moviedbkotlin.data.Genre
import io.reactivex.Single
import retrofit2.http.GET

/**
 * Created: 12/13/18.
 * By: Sang
 * Description:
 */
interface GenreApi {

    @GET("genre/movie/list")
    fun getGenres(): Single<GenreResponse>
}


class GenreResponse(
    @SerializedName("genres")
    var genres: List<Genre> = listOf()
)

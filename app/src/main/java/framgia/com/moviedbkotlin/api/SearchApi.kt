package framgia.com.moviedbkotlin.api

import framgia.com.moviedbkotlin.data.Movie
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created: 12/13/18.
 * By: Sang
 * Description:
 */
private const val PARAM_PAGE = "page"
private const val PARAM_QUERY = "query"

interface SearchApi {

    @GET("search/movie")
    fun getSearchMovies(
        @Query(PARAM_QUERY) query: String,
        @Query(PARAM_PAGE) page: Int
    ): Observable<ListResponse<Movie>>
}

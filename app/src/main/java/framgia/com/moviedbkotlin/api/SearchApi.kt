package framgia.com.moviedbkotlin.api

import framgia.com.moviedbkotlin.data.Movie
import framgia.com.moviedbkotlin.repository.Constants
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created: 12/13/18.
 * By: Sang
 * Description:
 */


interface SearchApi {

    @GET("search/movie")
    fun getSearchMovies(
        @Query(Constants.PARAM_QUERY) query: String,
        @Query(Constants.PARAM_PAGE) page: Int
    ): Observable<ListResponse<Movie>>
}

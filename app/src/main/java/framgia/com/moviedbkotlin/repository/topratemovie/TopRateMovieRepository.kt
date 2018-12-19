package framgia.com.moviedbkotlin.repository.topratemovie

import androidx.lifecycle.Transformations.switchMap
import androidx.paging.LivePagedListBuilder
import framgia.com.moviedbkotlin.api.MovieApi
import framgia.com.moviedbkotlin.data.Movie
import framgia.com.moviedbkotlin.repository.Listing
import java.util.concurrent.Executor

/**
 * Created: 12/18/18.
 * By: Sang
 * Description:
 */
class TopRateMovieRepository(
    private val movieApi: MovieApi,
    private val retryExecutor: Executor
) {

    fun getTopRateMovies(pageSize: Int): Listing<Movie> {
        val sourceFactory = TopRateMovieFactoryDataSource(movieApi, retryExecutor)
        val livePagedList =
            LivePagedListBuilder(sourceFactory, pageSize).setFetchExecutor(retryExecutor).build()
        val networkState = switchMap(sourceFactory.sourceLiveData) { it.networkState }
        val refreshState = switchMap(sourceFactory.sourceLiveData) { it.initialState }
        return Listing(
            pagedList = livePagedList,
            networkState = networkState,
            refreshState = refreshState,
            refresh = { sourceFactory.sourceLiveData.value?.invalidate() },
            retry = { sourceFactory.sourceLiveData.value?.retryWhenAllFailed() }
        )
    }
}

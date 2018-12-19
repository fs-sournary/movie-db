package framgia.com.moviedbkotlin.repository.popularmovie

import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import framgia.com.moviedbkotlin.api.MovieApi
import framgia.com.moviedbkotlin.data.Movie
import framgia.com.moviedbkotlin.repository.Listing
import java.util.concurrent.Executor

/**
 * Created: 17/12/2018
 * By: Sang
 * Description:
 */
class PopularMoviesRepository(
    private val movieApi: MovieApi,
    private val networkExecutor: Executor
) {

    fun getPopularMovies(pageSize: Int): Listing<Movie> {
        val sourceFactory = PopularMoviesDataSourceFactory(movieApi, networkExecutor)
        val livePagedList = LivePagedListBuilder(sourceFactory, pageSize)
            .setFetchExecutor(networkExecutor)
            .build()
        val refreshState =
            Transformations.switchMap(sourceFactory.sourceLiveData) { it.initialLoadState }
        val networkState =
            Transformations.switchMap(sourceFactory.sourceLiveData) { it.networkState }
        return Listing(
            pagedList = livePagedList,
            networkState = networkState,
            refreshState = refreshState,
            refresh = { sourceFactory.sourceLiveData.value?.invalidate() },
            retry = { sourceFactory.sourceLiveData.value?.retryWhenAllFailed() }
        )
    }
}
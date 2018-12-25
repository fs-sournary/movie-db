package framgia.com.moviedbkotlin.repository

import androidx.lifecycle.Transformations.switchMap
import androidx.paging.LivePagedListBuilder
import framgia.com.moviedbkotlin.api.MovieApi
import framgia.com.moviedbkotlin.data.Movie
import framgia.com.moviedbkotlin.repository.movie.NowPlayingMovieDataSource
import framgia.com.moviedbkotlin.repository.movie.PopularMoviesDataSource
import framgia.com.moviedbkotlin.repository.movie.TopRateMovieDataSource
import framgia.com.moviedbkotlin.repository.movie.UpcomingMovieDataSource
import framgia.com.moviedbkotlin.rx.SchedulerProvider
import java.util.concurrent.Executor

/**
 * Created: 20/12/2018
 * By: Sang
 * Description:
 */
class MovieRepository(
    private val movieApi: MovieApi,
    private val schedulerProvider: SchedulerProvider,
    private val networkExecutor: Executor
) {

    fun getPopularMovies(): PagingResult<Movie> {
        val sourceFactory =
            PopularMoviesDataSource.Factory(movieApi, schedulerProvider, networkExecutor)
        val livePagedList = LivePagedListBuilder(sourceFactory, Constants.PAGE_SIZE)
            .setFetchExecutor(networkExecutor)
            .build()
        val sourceData = sourceFactory.sourceLiveData
        return PagingResult(
            pagedList = livePagedList,
            networkState = switchMap(sourceData) { it.initialLoadState },
            refreshState = switchMap(sourceData) { it.networkState },
            refresh = { sourceData.value?.invalidate() },
            retry = { sourceData.value?.retryWhenAllFailed() },
            clear = { sourceData.value?.clear() }
        )
    }

    fun getTopRateMovies(): PagingResult<Movie> {
        val sourceFactory =
            TopRateMovieDataSource.Factory(movieApi, schedulerProvider, networkExecutor)
        val livePagedList = LivePagedListBuilder(sourceFactory, Constants.PAGE_SIZE)
            .setFetchExecutor(networkExecutor)
            .build()
        val sourceData = sourceFactory.sourceLiveData
        return PagingResult(
            pagedList = livePagedList,
            networkState = switchMap(sourceData) { it.networkState },
            refreshState = switchMap(sourceData) { it.initialState },
            refresh = { sourceData.value?.invalidate() },
            retry = { sourceData.value?.retryWhenAllFailed() },
            clear = { sourceData.value?.clear() }
        )
    }

    fun getUpcomingMovies(): PagingResult<Movie> {
        val sourceFactory =
            UpcomingMovieDataSource.Factory(movieApi, schedulerProvider, networkExecutor)
        val livePagedList = LivePagedListBuilder(sourceFactory, Constants.PAGE_SIZE)
            .setFetchExecutor(networkExecutor)
            .build()
        val sourceData = sourceFactory.sourceLiveData
        return PagingResult(
            pagedList = livePagedList,
            networkState = switchMap(sourceData) { it.networkState },
            refreshState = switchMap(sourceData) { it.initialState },
            refresh = { sourceData.value?.invalidate() },
            retry = { sourceData.value?.retryWhenAllFailed() },
            clear = { sourceData.value?.clear() }
        )
    }

    fun getNowPlayingMovies(): PagingResult<Movie> {
        val sourceFactory =
            NowPlayingMovieDataSource.Factory(movieApi, schedulerProvider, networkExecutor)
        val livePageList = LivePagedListBuilder(sourceFactory, Constants.PAGE_SIZE)
            .setFetchExecutor(networkExecutor)
            .build()
        val sourceData = sourceFactory.sourceLiveData
        return PagingResult(
            pagedList = livePageList,
            networkState = switchMap(sourceData) { it.networkState },
            refreshState = switchMap(sourceData) { it.initialState },
            refresh = { sourceData.value?.invalidate() },
            retry = { sourceData.value?.retryWhenAllFailed() },
            clear = { sourceData.value?.clear() }
        )
    }
}

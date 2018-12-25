package framgia.com.moviedbkotlin.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations.switchMap
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
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

    fun getPopularMovies(): NetworkResource<LiveData<PagedList<Movie>>> {
        val sourceFactory =
            PopularMoviesDataSource.Factory(movieApi, schedulerProvider, networkExecutor)
        val livePagedList = LivePagedListBuilder(sourceFactory, Constants.PAGE_SIZE)
            .setFetchExecutor(networkExecutor)
            .build()
        val sourceData = sourceFactory.sourceLiveData
        return NetworkResource(
            data = livePagedList,
            networkState = switchMap(sourceData) { it.networkState },
            refreshState = switchMap(sourceData) { it.initialLoadState },
            refresh = { sourceData.value?.invalidate() },
            retry = { sourceData.value?.retryWhenAllFailed() },
            clear = { sourceData.value?.clear() }
        )
    }

    fun getTopRateMovies(): NetworkResource<LiveData<PagedList<Movie>>> {
        val sourceFactory =
            TopRateMovieDataSource.Factory(movieApi, schedulerProvider, networkExecutor)
        val livePagedList = LivePagedListBuilder(sourceFactory, Constants.PAGE_SIZE)
            .setFetchExecutor(networkExecutor)
            .build()
        val sourceData = sourceFactory.sourceLiveData
        return NetworkResource(
            data = livePagedList,
            networkState = switchMap(sourceData) { it.networkState },
            refreshState = switchMap(sourceData) { it.initialState },
            refresh = { sourceData.value?.invalidate() },
            retry = { sourceData.value?.retryWhenAllFailed() },
            clear = { sourceData.value?.clear() }
        )
    }

    fun getUpcomingMovies(): NetworkResource<LiveData<PagedList<Movie>>> {
        val sourceFactory =
            UpcomingMovieDataSource.Factory(movieApi, schedulerProvider, networkExecutor)
        val livePagedList = LivePagedListBuilder(sourceFactory, Constants.PAGE_SIZE)
            .setFetchExecutor(networkExecutor)
            .build()
        val sourceData = sourceFactory.sourceLiveData
        return NetworkResource(
            data = livePagedList,
            networkState = switchMap(sourceData) { it.networkState },
            refreshState = switchMap(sourceData) { it.initialState },
            refresh = { sourceData.value?.invalidate() },
            retry = { sourceData.value?.retryWhenAllFailed() },
            clear = { sourceData.value?.clear() }
        )
    }

    fun getNowPlayingMovies(): NetworkResource<LiveData<PagedList<Movie>>> {
        val sourceFactory =
            NowPlayingMovieDataSource.Factory(movieApi, schedulerProvider, networkExecutor)
        val livePageList = LivePagedListBuilder(sourceFactory, Constants.PAGE_SIZE)
            .setFetchExecutor(networkExecutor)
            .build()
        val sourceData = sourceFactory.sourceLiveData
        return NetworkResource(
            data = livePageList,
            networkState = switchMap(sourceData) { it.networkState },
            refreshState = switchMap(sourceData) { it.initialState },
            refresh = { sourceData.value?.invalidate() },
            retry = { sourceData.value?.retryWhenAllFailed() },
            clear = { sourceData.value?.clear() }
        )
    }
}

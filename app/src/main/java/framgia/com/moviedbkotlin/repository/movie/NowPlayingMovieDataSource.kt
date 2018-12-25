package framgia.com.moviedbkotlin.repository.movie

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import androidx.paging.PageKeyedDataSource
import framgia.com.moviedbkotlin.api.MovieApi
import framgia.com.moviedbkotlin.data.Movie
import framgia.com.moviedbkotlin.repository.Constants
import framgia.com.moviedbkotlin.repository.NetworkState
import framgia.com.moviedbkotlin.rx.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable
import java.util.concurrent.Executor

/**
 * Created: 20/12/2018
 * By: Sang
 * Description:
 */
class NowPlayingMovieDataSource(
    private val movieApi: MovieApi,
    private val schedulerProvider: SchedulerProvider,
    private val networkExecutor: Executor
) : PageKeyedDataSource<Int, Movie>() {

    private var retry: (() -> Unit)? = null
    private val compositeDisposable = CompositeDisposable()

    val initialState = MutableLiveData<NetworkState>()
    val networkState = MutableLiveData<NetworkState>()

    fun retryWhenAllFailed() {
        val preRetry = retry
        retry = null
        preRetry?.let { networkExecutor.execute { it.invoke() } }
    }

    fun clear() {
        compositeDisposable.clear()
    }

    override fun loadInitial(
        params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, Movie>
    ) {
        initialState.postValue(NetworkState.RUNNING)
        networkState.postValue(NetworkState.RUNNING)
        val disposable = movieApi.getNowPlayingMovies(Constants.INIT_PAGE)
            .subscribeOn(schedulerProvider.io())
            .observeOn(schedulerProvider.ui())
            .subscribe(
                {
                    initialState.postValue(NetworkState.SUCCESS)
                    networkState.postValue(NetworkState.SUCCESS)
                    retry = null
                    callback.onResult(it.results, null, Constants.INIT_PAGE + 1)
                },
                {
                    val error = NetworkState.error(it.message ?: Constants.DEFAULT_ERROR)
                    initialState.postValue(error)
                    networkState.postValue(error)
                    retry = { loadInitial(params, callback) }
                }
            )
        compositeDisposable.add(disposable)
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Movie>) {
        networkState.postValue(NetworkState.RUNNING)
        val disposable = movieApi.getNowPlayingMovies(params.key)
            .subscribeOn(schedulerProvider.io())
            .observeOn(schedulerProvider.ui())
            .subscribe(
                {
                    networkState.postValue(NetworkState.SUCCESS)
                    retry = null
                    val data = it.results
                    val nextPage = if (it.totalPages == params.key) null else params.key + 1
                    callback.onResult(data, nextPage)
                },
                {
                    val error = NetworkState.error(it.message ?: Constants.DEFAULT_ERROR)
                    networkState.postValue(error)
                    retry = { loadAfter(params, callback) }
                }
            )
        compositeDisposable.add(disposable)
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Movie>) {
        // Ignored
    }

    class Factory(
        private val movieApi: MovieApi,
        private val schedulerProvider: SchedulerProvider,
        private val networkExecutor: Executor
    ) : DataSource.Factory<Int, Movie>() {

        val sourceLiveData = MutableLiveData<NowPlayingMovieDataSource>()

        override fun create(): DataSource<Int, Movie> {
            val source = NowPlayingMovieDataSource(movieApi, schedulerProvider, networkExecutor)
            sourceLiveData.postValue(source)
            return source
        }
    }
}
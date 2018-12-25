package framgia.com.moviedbkotlin.repository.movie

import androidx.lifecycle.LiveData
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
 * Created: 17/12/2018
 * By: Sang
 * Description:
 */
class PopularMoviesDataSource(
    private val movieApi: MovieApi,
    private val schedulerProvider: SchedulerProvider,
    private val retryExecutor: Executor
) : PageKeyedDataSource<Int, Movie>() {

    private var retry: (() -> Unit)? = null // Retry event
    private val compositeDisposable = CompositeDisposable()

    private val _initialLoadState = MutableLiveData<NetworkState>()
    private val _networkState = MutableLiveData<NetworkState>()

    val initialLoadState: LiveData<NetworkState> = _initialLoadState
    val networkState: LiveData<NetworkState> = _networkState

    fun retryWhenAllFailed() {
        val prevRetry = retry
        retry = null
        prevRetry?.let { retryExecutor.execute { it.invoke() } }
    }

    /**
     * Init load data.
     */
    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, Movie>
    ) {
        _initialLoadState.postValue(NetworkState.RUNNING)
        _networkState.postValue(NetworkState.RUNNING)
        val disposable = movieApi.getPopularMovies(Constants.INIT_PAGE)
            .subscribeOn(schedulerProvider.io())
            .observeOn(schedulerProvider.ui())
            .subscribe(
                {
                    _initialLoadState.postValue(NetworkState.SUCCESS)
                    _networkState.postValue(NetworkState.SUCCESS)
                    retry = null
                    callback.onResult(it.results, null, Constants.INIT_PAGE + 1)
                },
                {
                    val error = NetworkState.error(it.message ?: Constants.DEFAULT_ERROR)
                    _initialLoadState.postValue(error)
                    _networkState.postValue(error)
                    retry = { loadInitial(params, callback) }
                }
            )
        compositeDisposable.add(disposable)
    }

    /**
     * Append data with the page with key specified by LoadParams.key in callback.onResult()
     */
    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Movie>) {
        _networkState.postValue(NetworkState.RUNNING)
        val disposable = movieApi.getPopularMovies(params.key)
            .subscribeOn(schedulerProvider.io())
            .observeOn(schedulerProvider.ui())
            .subscribe(
                {
                    retry = null
                    _networkState.postValue(NetworkState.SUCCESS)
                    val body = it.results
                    val nextKey = if (params.key == it.totalPages) null else params.key + 1
                    callback.onResult(body, nextKey)
                },
                {
                    retry = { loadAfter(params, callback) }
                    val error = NetworkState.error(it.message ?: Constants.DEFAULT_ERROR)
                    _networkState.postValue(error)
                }
            )
        compositeDisposable.add(disposable)
    }

    /**
     * Prepend (nối trước) page with the key specified by LoadParams.key in callback.onResult()
     */
    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Movie>) {
        // Ignored, since we only append data to init load.
    }

    fun clear() {
        compositeDisposable.clear()
    }

    class Factory(
        private val movieApi: MovieApi,
        private val schedulerProvider: SchedulerProvider,
        private val retryExecutor: Executor
    ) : DataSource.Factory<Int, Movie>() {

        private val _sourceLiveData = MutableLiveData<PopularMoviesDataSource>()

        val sourceLiveData: LiveData<PopularMoviesDataSource> = _sourceLiveData

        override fun create(): DataSource<Int, Movie> {
            val source = PopularMoviesDataSource(movieApi, schedulerProvider, retryExecutor)
            _sourceLiveData.postValue(source)
            return source
        }
    }
}

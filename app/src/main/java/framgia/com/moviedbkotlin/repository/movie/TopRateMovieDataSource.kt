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
 * Created: 12/18/18.
 * By: Sang
 * Description:
 */
class TopRateMovieDataSource(
    private val movieApi: MovieApi,
    private val schedulerProvider: SchedulerProvider,
    private val retryExecutor: Executor
) : PageKeyedDataSource<Int, Movie>() {

    private var retry: (() -> Unit)? = null
    private val compositeDisposable = CompositeDisposable()

    private val _networkState = MutableLiveData<NetworkState>()
    private val _initialState = MutableLiveData<NetworkState>()

    val networkState: LiveData<NetworkState> = _networkState
    val initialState: LiveData<NetworkState> = _initialState

    fun retryWhenAllFailed() {
        val prevRetry = retry
        retry = null
        prevRetry?.let { retryExecutor.execute { it.invoke() } }
    }

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, Movie>
    ) {
        _networkState.postValue(NetworkState.RUNNING)
        _initialState.postValue(NetworkState.RUNNING)
        val disposable = movieApi.getTopRateMovies(Constants.INIT_PAGE)
            .subscribeOn(schedulerProvider.io())
            .observeOn(schedulerProvider.ui())
            .subscribe(
                {
                    _initialState.postValue(NetworkState.SUCCESS)
                    _networkState.postValue(NetworkState.SUCCESS)
                    retry = null
                    callback.onResult(it.results, null, Constants.INIT_PAGE + 1)
                },
                {
                    val error = NetworkState.error(it.message ?: Constants.DEFAULT_ERROR)
                    _initialState.postValue(error)
                    _networkState.postValue(error)
                    retry = { loadInitial(params, callback) }
                }
            )
        compositeDisposable.add(disposable)
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Movie>) {
        _networkState.postValue(NetworkState.RUNNING)
        val disposable = movieApi.getTopRateMovies(params.key)
            .subscribeOn(schedulerProvider.io())
            .observeOn(schedulerProvider.ui())
            .subscribe(
                {
                    _networkState.postValue(NetworkState.SUCCESS)
                    retry = null
                    val data = it.results
                    val nextKey = if (params.key == it.totalPages) null else params.key + 1
                    callback.onResult(data, nextKey)
                },
                {
                    val error = NetworkState.error(it.message ?: Constants.DEFAULT_ERROR)
                    _networkState.postValue(error)
                    retry = { loadAfter(params, callback) }
                }
            )
        compositeDisposable.add(disposable)
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Movie>) {
        // Ignored
    }

    fun clear() {
        compositeDisposable.clear()
    }

    class Factory(
        private val movieApi: MovieApi,
        private val schedulerProvider: SchedulerProvider,
        private val retryExecutor: Executor
    ) : DataSource.Factory<Int, Movie>() {

        private val _sourceLiveData = MutableLiveData<TopRateMovieDataSource>()

        val sourceLiveData: LiveData<TopRateMovieDataSource> = _sourceLiveData

        override fun create(): DataSource<Int, Movie> {
            val source = TopRateMovieDataSource(movieApi, schedulerProvider, retryExecutor)
            _sourceLiveData.postValue(source)
            return source
        }
    }
}

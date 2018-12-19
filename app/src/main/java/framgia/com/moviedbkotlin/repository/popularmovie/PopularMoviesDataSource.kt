package framgia.com.moviedbkotlin.repository.popularmovie

import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import framgia.com.moviedbkotlin.api.ListResponse
import framgia.com.moviedbkotlin.api.MovieApi
import framgia.com.moviedbkotlin.data.Movie
import framgia.com.moviedbkotlin.repository.NetworkState
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.concurrent.Executor

/**
 * Created: 17/12/2018
 * By: Sang
 * Description:
 */
class PopularMoviesDataSource(
    private val movieApi: MovieApi,
    private val retryExecutor: Executor
) : PageKeyedDataSource<Int, Movie>() {

    // Retry event
    private var retry: (() -> Any)? = null

    val initialLoadState = MutableLiveData<NetworkState>()
    val networkState = MutableLiveData<NetworkState>()

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
        initialLoadState.postValue(NetworkState.RUNNING)
        networkState.postValue(NetworkState.RUNNING)
        movieApi.getPopularMovies(1).enqueue(object : Callback<ListResponse<Movie>> {

            override fun onFailure(call: Call<ListResponse<Movie>>, t: Throwable) {
                val error = NetworkState.error(t.message ?: "Unknown error")
                initialLoadState.postValue(error)
                networkState.postValue(error)
                retry = { loadInitial(params, callback) }
            }

            override fun onResponse(
                call: Call<ListResponse<Movie>>,
                response: Response<ListResponse<Movie>>
            ) {
                if (response.isSuccessful) {
                    networkState.postValue(NetworkState.SUCCESS)
                    initialLoadState.postValue(NetworkState.SUCCESS)
                    retry = null
                    val data = response.body()?.results ?: emptyList()
                    callback.onResult(data, null, 2)
                } else {
                    val error = NetworkState.error("Error code: ${response.code()}")
                    networkState.postValue(error)
                    initialLoadState.postValue(error)
                    retry = { loadInitial(params, callback) }
                }
            }
        })
    }

    /**
     * Append data with the page with key specified by LoadParams.key in callback.onResult()
     */
    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Movie>) {
        networkState.postValue(NetworkState.RUNNING)
        movieApi.getPopularMovies(params.key).enqueue(object : Callback<ListResponse<Movie>> {

            override fun onFailure(call: Call<ListResponse<Movie>>, t: Throwable) {
                val error = NetworkState.error(t.message ?: "Unknown error")
                networkState.postValue(error)
                retry = { loadAfter(params, callback) }
            }

            override fun onResponse(
                call: Call<ListResponse<Movie>>,
                response: Response<ListResponse<Movie>>
            ) {
                if (response.isSuccessful) {
                    networkState.postValue(NetworkState.SUCCESS)
                    retry = null
                    val body = response.body()
                    val data = body?.results ?: emptyList()
                    val nextKey = if (params.key == body?.totalPages) null else params.key + 1
                    callback.onResult(data, nextKey)
                } else {
                    val error = NetworkState.error("Error code: ${response.code()}")
                    networkState.postValue(error)
                    retry = { loadAfter(params, callback) }
                }
            }
        })
    }

    /**
     * Prepend (nối trước) page with the key specified by LoadParams.key in callback.onResult()
     */
    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Movie>) {
        // Ignored, since we only append data to init load.
    }
}

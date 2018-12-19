package framgia.com.moviedbkotlin.repository.topratemovie

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
 * Created: 12/18/18.
 * By: Sang
 * Description:
 */
class TopRateMovieDataSource(
    private val movieApi: MovieApi,
    private val retryExecutor: Executor
) : PageKeyedDataSource<Int, Movie>() {

    private var retry: (() -> Unit)? = null

    val networkState = MutableLiveData<NetworkState>()
    val initialState = MutableLiveData<NetworkState>()

    fun retryWhenAllFailed() {
        val prevRetry = retry
        retry = null
        prevRetry?.let { retryExecutor.execute { it.invoke() } }
    }

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, Movie>
    ) {
        networkState.postValue(NetworkState.RUNNING)
        initialState.postValue(NetworkState.RUNNING)
        movieApi.getTopRateMovies(INIT_PAGE).enqueue(object : Callback<ListResponse<Movie>> {

            override fun onFailure(call: Call<ListResponse<Movie>>, t: Throwable) {
                val error = NetworkState.error(t.message ?: DEFAULT_ERROR)
                networkState.postValue(error)
                initialState.postValue(error)
                retry = { loadInitial(params, callback) }
            }

            override fun onResponse(
                call: Call<ListResponse<Movie>>,
                response: Response<ListResponse<Movie>>
            ) {
                if (response.isSuccessful) {
                    networkState.postValue(NetworkState.SUCCESS)
                    initialState.postValue(NetworkState.SUCCESS)
                    val data = response.body()?.results ?: emptyList()
                    callback.onResult(data, null, NEXT_INIT_PAGE)
                } else {
                    val error = NetworkState.error("Error code: ${response.code()}")
                    networkState.postValue(error)
                    initialState.postValue(error)
                    retry = { loadInitial(params, callback) }
                }
            }
        })
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Movie>) {
        networkState.postValue(NetworkState.RUNNING)
        initialState.postValue(NetworkState.RUNNING)
        movieApi.getTopRateMovies(params.key).enqueue(object : Callback<ListResponse<Movie>> {

            override fun onFailure(call: Call<ListResponse<Movie>>, t: Throwable) {
                val error = NetworkState.error(t.message ?: DEFAULT_ERROR)
                networkState.postValue(error)
                initialState.postValue(error)
                retry = { loadAfter(params, callback) }
            }

            override fun onResponse(
                call: Call<ListResponse<Movie>>,
                response: Response<ListResponse<Movie>>
            ) {
                if (response.isSuccessful) {
                    networkState.postValue(NetworkState.SUCCESS)
                    initialState.postValue(NetworkState.SUCCESS)
                    val body = response.body()
                    val data = body?.results ?: emptyList()
                    val nextKey = if (body?.totalPages == params.key) null else params.key + 1
                    callback.onResult(data, nextKey)
                } else {
                    val error = NetworkState.error("Error code: ${response.code()}")
                    networkState.postValue(error)
                    initialState.postValue(error)
                    retry = { loadAfter(params, callback) }
                }
            }
        })
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Movie>) {
        // Ignored
    }

    companion object {

        private const val INIT_PAGE = 1
        private const val NEXT_INIT_PAGE = 2
        private const val DEFAULT_ERROR = "Unknown error"
    }
}

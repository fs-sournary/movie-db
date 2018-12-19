package framgia.com.moviedbkotlin.repository.popularmovie

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import framgia.com.moviedbkotlin.api.MovieApi
import framgia.com.moviedbkotlin.data.Movie
import java.util.concurrent.Executor

/**
 * Created: 17/12/2018
 * By: Sang
 * Description:
 */
class PopularMoviesDataSourceFactory(
    private val movieApi: MovieApi,
    private val retryExecutor: Executor
) : DataSource.Factory<Int, Movie>() {

    val sourceLiveData = MutableLiveData<PopularMoviesDataSource>()

    override fun create(): DataSource<Int, Movie> {
        val source = PopularMoviesDataSource(movieApi, retryExecutor)
        sourceLiveData.postValue(source)
        return source
    }
}

package framgia.com.moviedbkotlin.repository.topratemovie

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import framgia.com.moviedbkotlin.api.MovieApi
import framgia.com.moviedbkotlin.data.Movie
import java.util.concurrent.Executor

/**
 * Created: 12/18/18.
 * By: Sang
 * Description:
 */
class TopRateMovieFactoryDataSource(
    private val movieApi: MovieApi,
    private val retryExecutor: Executor
) : DataSource.Factory<Int, Movie>() {

    val sourceLiveData = MutableLiveData<TopRateMovieDataSource>()

    override fun create(): DataSource<Int, Movie> {
        val source = TopRateMovieDataSource(movieApi, retryExecutor)
        sourceLiveData.postValue(source)
        return source
    }
}

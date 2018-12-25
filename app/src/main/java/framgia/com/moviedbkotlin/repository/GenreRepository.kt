package framgia.com.moviedbkotlin.repository

import androidx.lifecycle.LiveData
import framgia.com.moviedbkotlin.api.GenreApi
import framgia.com.moviedbkotlin.data.Genre
import framgia.com.moviedbkotlin.repository.genre.GenreDataSource
import framgia.com.moviedbkotlin.rx.SchedulerProvider

/**
 * Created: 21/12/2018
 * By: Sang
 * Description:
 */
class GenreRepository(
    private val genreApi: GenreApi,
    private val schedulerProvider: SchedulerProvider
) {

    fun getGenres(): NetworkResource<LiveData<List<Genre>>> {
        val dataSource = GenreDataSource(genreApi, schedulerProvider)
        return NetworkResource(
            data = dataSource.genres,
            networkState = dataSource.networkState,
            retry = dataSource.retry,
            clear = { dataSource.clear() }
        )
    }
}



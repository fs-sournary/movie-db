package framgia.com.moviedbkotlin.repository.genre

import androidx.lifecycle.MutableLiveData
import framgia.com.moviedbkotlin.api.GenreApi
import framgia.com.moviedbkotlin.data.Genre
import framgia.com.moviedbkotlin.repository.Constants
import framgia.com.moviedbkotlin.repository.NetworkState
import framgia.com.moviedbkotlin.rx.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable

/**
 * Created: 21/12/2018
 * By: Sang
 * Description:
 */
class GenreDataSource(
    private val genreApi: GenreApi,
    private val schedulerProvider: SchedulerProvider
) {

    private val compositeDisposable = CompositeDisposable()

    val networkState = MutableLiveData<NetworkState>()
    val genres = MutableLiveData<List<Genre>>()

    fun getGenres() {
        networkState.postValue(NetworkState.RUNNING)
        val disposable = genreApi.getGenres()
            .observeOn(schedulerProvider.ui())
            .subscribeOn(schedulerProvider.io())
            .subscribe(
                {
                    networkState.postValue(NetworkState.SUCCESS)
                    genres.postValue(it.genres)
                },
                {
                    val error = NetworkState.error(it.message ?: Constants.DEFAULT_ERROR)
                    networkState.postValue(error)
                }
            )
        compositeDisposable.add(disposable)
    }

    fun clear() {
        compositeDisposable.clear()
    }
}

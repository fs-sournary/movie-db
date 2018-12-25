package framgia.com.moviedbkotlin.repository.genre

import androidx.lifecycle.LiveData
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

    var retry: (() -> Unit)? = null
    private val compositeDisposable = CompositeDisposable()

    private val _networkState = MutableLiveData<NetworkState>()
    private val _genres = MutableLiveData<List<Genre>>()

    val networkState: LiveData<NetworkState> = _networkState
    val genres: LiveData<List<Genre>> = _genres

    init {
        fetchGenres()
    }

    private fun fetchGenres() {
        _networkState.postValue(NetworkState.RUNNING)
        val disposable = genreApi.getGenres()
            .observeOn(schedulerProvider.ui())
            .subscribeOn(schedulerProvider.io())
            .subscribe(
                {
                    _networkState.postValue(NetworkState.SUCCESS)
                    if (retry != null) retry = null
                    _genres.postValue(it.genres)
                },
                {
                    val error = NetworkState.error(it.message ?: Constants.DEFAULT_ERROR)
                    retry = { genreApi.getGenres() }
                    _networkState.postValue(error)
                }
            )
        compositeDisposable.add(disposable)
    }

    fun clear() {
        compositeDisposable.clear()
    }
}

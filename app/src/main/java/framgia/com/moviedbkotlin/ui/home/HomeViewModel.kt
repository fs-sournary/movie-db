package framgia.com.moviedbkotlin.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations.map
import androidx.lifecycle.Transformations.switchMap
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import framgia.com.moviedbkotlin.data.Genre
import framgia.com.moviedbkotlin.data.Movie
import framgia.com.moviedbkotlin.repository.*

/**
 * Created: 12/16/18.
 * By: Sang
 * Description:
 */
class HomeViewModel(
    private val movieRepository: MovieRepository,
    private val genreRepository: GenreRepository
) : ViewModel() {

    // Save scroll position of RecyclerView after it is stop or destroy
    var movieScrollPosition: Int = 0

    // Movie
    private var movieResult = MutableLiveData<NetworkResource<LiveData<PagedList<Movie>>>>()
    val movies = switchMap(movieResult) { it.data }!!
    val movieNetworkState = switchMap(movieResult) { it.networkState }!!
    private val movieRefreshState = switchMap(movieResult) { it.refreshState }
    val isShowLoading = map(movieRefreshState) { it == NetworkState.RUNNING }!!

    // Genre
    private var genreResult = MutableLiveData<NetworkResource<LiveData<List<Genre>>>>()
    val genres = switchMap(genreResult) { it.data }!!

    fun loadGenres(){
        genreResult.value = genreRepository.getGenres()
    }

    fun loadPopularMovies() {
        movieResult.value = movieRepository.getPopularMovies()
    }

    fun loadTopRateMovies() {
        movieResult.value = movieRepository.getTopRateMovies()
    }

    fun loadNowPlayingMovies() {
        movieResult.value = movieRepository.getNowPlayingMovies()
    }

    fun loadUpcomingMovies() {
        movieResult.value = movieRepository.getUpcomingMovies()
    }
}

package framgia.com.moviedbkotlin.ui.home

import androidx.lifecycle.Transformations.map
import androidx.lifecycle.ViewModel
import framgia.com.moviedbkotlin.repository.NetworkState
import framgia.com.moviedbkotlin.repository.popularmovie.PopularMoviesRepository

/**
 * Created: 12/16/18.
 * By: Sang
 * Description:
 */
private const val PAGE_SIZE = 20

class HomeViewModel(popularMoviesRepository: PopularMoviesRepository) :
    ViewModel() {

    // Save scroll position of RecyclerView after it is stop or destroy
    var popularMovieScrollPosition: Int = 0
    private val result = popularMoviesRepository.getPopularMovies(PAGE_SIZE)

    val popularMovies = result.pagedList
    val networkState = result.networkState
    private val refreshState = result.refreshState!!
    val isShowLoading = map(refreshState) { it == NetworkState.RUNNING }!!
}

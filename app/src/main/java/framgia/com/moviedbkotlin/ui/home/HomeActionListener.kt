package framgia.com.moviedbkotlin.ui.home

import framgia.com.moviedbkotlin.data.Movie

/**
 * Created: 17/12/2018
 * By: Sang
 * Description:
 */
interface HomeActionListener {

    fun retry()

    fun clickPopularMovieItem(movie: Movie)
}

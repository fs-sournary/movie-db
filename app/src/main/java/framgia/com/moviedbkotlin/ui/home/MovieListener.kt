package framgia.com.moviedbkotlin.ui.home

import framgia.com.moviedbkotlin.data.Movie

/**
 * Created: 17/12/2018
 * By: Sang
 * Description:
 */
interface MovieListener {

    fun retry()

    fun onClickMovie(movie: Movie)
}

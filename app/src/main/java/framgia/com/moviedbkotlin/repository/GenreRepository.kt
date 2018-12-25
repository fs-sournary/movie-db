package framgia.com.moviedbkotlin.repository

import framgia.com.moviedbkotlin.repository.genre.GenreDataSource

/**
 * Created: 21/12/2018
 * By: Sang
 * Description:
 */
class GenreRepository(private val genreDataSource: GenreDataSource) {

    fun getGenres(): GenreResult {
        genreDataSource.getGenres()
        return GenreResult(genreDataSource.genres, genreDataSource.networkState)
    }
}



package framgia.com.moviedbkotlin.di

import framgia.com.moviedbkotlin.db.MovieDB
import framgia.com.moviedbkotlin.db.MovieDao
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module.module

/**
 * Created: 12/13/18.
 * By: Sang
 * Description:
 */
val localModule = module {
    single { MovieDB.newInstance(androidApplication()) }
    single { createMovieDao(get()) }
}

private fun createMovieDao(movieDB: MovieDB): MovieDao = movieDB.getMovieDao()

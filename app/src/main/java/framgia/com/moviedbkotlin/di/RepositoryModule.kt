package framgia.com.moviedbkotlin.di

import framgia.com.moviedbkotlin.repository.GenreRepository
import framgia.com.moviedbkotlin.repository.MovieRepository
import framgia.com.moviedbkotlin.repository.genre.GenreDataSource
import org.koin.dsl.module.module

/**
 * Created: 12/13/18.
 * By: Sang
 * Description:
 */
val localDataSourceModule = module {
    single { GenreDataSource(get(), get()) }
}

val remoteDataSourceModule = module {
}

val repositoryModule = module {
    single { MovieRepository(get(), get(), get()) }
    single { GenreRepository(get()) }
}

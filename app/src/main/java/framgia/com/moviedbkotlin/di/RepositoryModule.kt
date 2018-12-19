package framgia.com.moviedbkotlin.di

import framgia.com.moviedbkotlin.repository.popularmovie.PopularMoviesRepository
import org.koin.dsl.module.module

/**
 * Created: 12/13/18.
 * By: Sang
 * Description:
 */
val localDataSourceModule = module {

}

val remoteDataSourceModule = module {
}

val repositoryModule = module {
    single { PopularMoviesRepository(get(), get()) }
}

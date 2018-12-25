package framgia.com.moviedbkotlin.di

import framgia.com.moviedbkotlin.MainViewModel
import framgia.com.moviedbkotlin.ui.account.AccountViewModel
import framgia.com.moviedbkotlin.ui.home.HomeViewModel
import framgia.com.moviedbkotlin.ui.library.LibraryViewModel
import framgia.com.moviedbkotlin.ui.moviedetail.MovieDetailViewModel
import framgia.com.moviedbkotlin.ui.watchlist.WatchListViewModel
import org.koin.androidx.viewmodel.experimental.builder.viewModel
import org.koin.dsl.module.module

/**
 * Created: 12/13/18.
 * By: Sang
 * Description:
 */
val viewModelModule = module {
    // Activity module
    viewModel<MainViewModel>()
    // Fragment module
    viewModel<HomeViewModel>()
    viewModel<LibraryViewModel>()
    viewModel<WatchListViewModel>()
    viewModel<AccountViewModel>()
    viewModel<MovieDetailViewModel>()
}

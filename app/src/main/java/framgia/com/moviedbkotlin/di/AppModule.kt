package framgia.com.moviedbkotlin.di

import framgia.com.moviedbkotlin.rx.AppSchedulerProvider
import framgia.com.moviedbkotlin.rx.SchedulerProvider
import org.koin.dsl.module.module

/**
 * Created: 12/13/18.
 * By: Sang
 * Description:
 */
val appModule = module {
    single { AppSchedulerProvider() as SchedulerProvider }
}

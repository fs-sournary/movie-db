package framgia.com.moviedbkotlin

import android.app.Application
import framgia.com.moviedbkotlin.di.*
import org.koin.android.ext.android.startKoin

/**
 * Created: 09/07/2018
 * By: Sang
 * Description:
 */
class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        val modules = listOf(
            appModule, localModule, networkModule, viewModelModule,
            localDataSourceModule, remoteDataSourceModule, repositoryModule
        )
        startKoin(this, modules)
    }
}

package framgia.com.moviedbkotlin.di

import framgia.com.moviedbkotlin.BuildConfig
import framgia.com.moviedbkotlin.api.GenreApi
import framgia.com.moviedbkotlin.api.MovieApi
import framgia.com.moviedbkotlin.api.SearchApi
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.Executor
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

/**
 * Created: 12/13/18.
 * By: Sang
 * Description:
 */
private const val LOGGING_INTERCEPTOR = "loggingInterceptor"
private const val HEADER_INTERCEPTOR = "headerInterceptor"
private const val CONNECTION_TIMEOUT = 10L
private const val READ_TIMEOUT = 10L
private const val WRITE_TIMEOUT = 10L

val networkModule = module {
    single(LOGGING_INTERCEPTOR) { createLoggingInterceptor() }
    single(HEADER_INTERCEPTOR) { createHeaderInterceptor() }
    single { getNetworkExecutor() }
    single { createOkHttpClient(get(LOGGING_INTERCEPTOR), get(HEADER_INTERCEPTOR)) }
    single { createRetrofit(get()) }
    single { createMovieApi(get()) }
    single { createSearchApi(get()) }
    single { createGenreApi(get()) }
}

private fun getNetworkExecutor(): Executor = Executors.newFixedThreadPool(5)

private fun createLoggingInterceptor(): Interceptor =
    HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }

private fun createHeaderInterceptor(): Interceptor =
    Interceptor {
        val request = it.request()
        val newUrl =
            request.url().newBuilder().addQueryParameter("api_key", BuildConfig.API_KEY).build()
        val newRequest = Request.Builder()
            .url(newUrl)
            .method(request.method(), request.body())
            .build()
        it.proceed(newRequest)
    }

private fun createOkHttpClient(
    loggingInterceptor: Interceptor,
    headerInterceptor: Interceptor
): OkHttpClient = OkHttpClient.Builder()
    .connectTimeout(CONNECTION_TIMEOUT, TimeUnit.SECONDS)
    .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
    .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
    .addInterceptor(loggingInterceptor)
    .addInterceptor(headerInterceptor)
    .build()

private fun createRetrofit(okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
    .baseUrl(BuildConfig.BASE_URL)
    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
    .addConverterFactory(GsonConverterFactory.create())
    .client(okHttpClient)
    .build()

private fun createMovieApi(retrofit: Retrofit): MovieApi = retrofit.create(MovieApi::class.java)

private fun createSearchApi(retrofit: Retrofit): SearchApi = retrofit.create(SearchApi::class.java)

private fun createGenreApi(retrofit: Retrofit): GenreApi = retrofit.create(GenreApi::class.java)

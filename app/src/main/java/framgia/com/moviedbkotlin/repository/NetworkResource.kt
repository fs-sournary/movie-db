package framgia.com.moviedbkotlin.repository

import androidx.lifecycle.LiveData

/**
 * Created: 17/12/2018
 * By: Sang
 * Description:
 */
data class NetworkResource<T>(
    val data: T,
    val networkState: LiveData<NetworkState>? = null,
    val refreshState: LiveData<NetworkState>? = null,
    val refresh: (() -> Unit)? = null,
    val retry: (() -> Unit)? = null,
    val clear: (() -> Unit)? = null
)

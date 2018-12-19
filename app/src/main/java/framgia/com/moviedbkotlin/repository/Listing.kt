package framgia.com.moviedbkotlin.repository

import androidx.lifecycle.LiveData
import androidx.paging.PagedList

/**
 * Created: 17/12/2018
 * By: Sang
 * Description:
 */
data class Listing<T>(
    val pagedList: LiveData<PagedList<T>>,
    val networkState: LiveData<NetworkState>,
    val refreshState: LiveData<NetworkState>?,
    val refresh: () -> Unit,
    val retry: () -> Unit
)

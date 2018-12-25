package framgia.com.moviedbkotlin.repository

import androidx.lifecycle.LiveData
import androidx.paging.PagedList

/**
 * Created: 17/12/2018
 * By: Sang
 * Description:
 */
data class PagingResult<T>(
    val pagedList: LiveData<PagedList<T>>,
    val networkState: LiveData<NetworkState>? = null,
    val refreshState: LiveData<NetworkState>? = null,
    val refresh: (() -> Unit)? = null,
    val retry: (() -> Unit)? = null,
    val clear: (() -> Unit)? = null
)

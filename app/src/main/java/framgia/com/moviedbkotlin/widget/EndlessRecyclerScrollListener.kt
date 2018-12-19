package framgia.com.moviedbkotlin.widget

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

/**
 * Created by fs-sournary.
 * Date: 7/10/18.
 * Description:
 */
private const val LOAD_MORE_OFFSET = 2

class EndlessRecyclerScrollListener(
    private val onLoadMore: () -> Unit,
    private val loadMoreOffset: Int = LOAD_MORE_OFFSET
) : RecyclerView.OnScrollListener() {

    private var isLoading = true
    private var lastVisibleItemPosition = 0
    private var totalItemCount = 0

    /**
     * Method is called:
     * - After the scroll is completed.
     * - Visible item range changes after a layout calculation (dx and dy = 0, load more event)
     */
    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        totalItemCount = recyclerView.adapter!!.itemCount
        lastVisibleItemPosition = when (recyclerView.layoutManager) {
            is LinearLayoutManager -> {
                (recyclerView.layoutManager as LinearLayoutManager).findLastVisibleItemPosition()
            }
            is GridLayoutManager -> {
                (recyclerView.layoutManager as GridLayoutManager).findLastVisibleItemPosition()
            }
            else -> {
                throw RuntimeException("UnSupport this layout manager: ${recyclerView.layoutManager}")
            }
        }
        if (isLoading) {
            isLoading = false
        }
        if (totalItemCount < lastVisibleItemPosition + loadMoreOffset) {
            onLoadMore.invoke()
            isLoading = true
        }
    }

    fun resetLoadMoreState() {
        isLoading = true
        lastVisibleItemPosition = 0
        totalItemCount = 0
    }
}

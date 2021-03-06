package framgia.com.moviedbkotlin.widget

import android.view.View
import androidx.recyclerview.widget.*

/**
 * Created: 18/12/2018
 * By: Sang
 * Description:
 */
class StartSnapHelper : LinearSnapHelper() {

    private var verticalHelper: OrientationHelper? = null
    private var horizontalHelper: OrientationHelper? = null

    /**
     * Snap a particular point within targetView or container View.
     * Calculate distance required to scroll to the targetView
     */
    override fun calculateDistanceToFinalSnap(
        layoutManager: RecyclerView.LayoutManager,
        targetView: View
    ): IntArray? {
        val out = IntArray(2)
        if (layoutManager.canScrollHorizontally()) {
            out[0] = distanceToStart(targetView, getHorizontalHelper(layoutManager))
        } else {
            out[0] = 0
        }

        if (layoutManager.canScrollVertically()) {
            out[1] = distanceToStart(targetView, getVerticalHelper(layoutManager))
        } else {
            out[1] = 0
        }
        return out
    }

    override fun findSnapView(layoutManager: RecyclerView.LayoutManager?): View? =
        when (layoutManager) {
            is LinearLayoutManager -> {
                if (layoutManager.canScrollHorizontally()) {
                    getStartView(layoutManager, getHorizontalHelper(layoutManager))
                } else {
                    getStartView(layoutManager, getVerticalHelper(layoutManager))
                }
            }
            else -> {
                throw java.lang.IllegalArgumentException("UnSupport layout manager: $layoutManager")
            }
        }


    private fun getStartView(
        layoutManager: RecyclerView.LayoutManager,
        helper: OrientationHelper
    ): View? =
        when (layoutManager) {
            is GridLayoutManager -> {
                with(layoutManager) {
                    // Layout Manager is GridLayoutManager with spanCount = 2, spanSize of 0 is 2
                    if (spanCount == 2
                        && spanSizeLookup.getSpanSize(0) == 2
                        && orientation == OrientationHelper.HORIZONTAL
                    ) {
                        val firstVisibleItemPosition = findFirstVisibleItemPosition()
                        val isLastItem = findLastCompletelyVisibleItemPosition() == itemCount - 1
                        if (firstVisibleItemPosition == RecyclerView.NO_POSITION || isLastItem) {
                            return null
                        }
                        val child = findViewByPosition(firstVisibleItemPosition)
                        return if (
                            helper.getDecoratedEnd(child) >= helper.getDecoratedMeasurement(child) / 2
                            && helper.getDecoratedEnd(child) > 0
                        ) {
                            child
                        } else {
                            when {
                                findLastCompletelyVisibleItemPosition() == itemCount - 1 -> null
                                firstVisibleItemPosition == 0 ->
                                    findViewByPosition(firstVisibleItemPosition + 1)
                                else -> findViewByPosition(firstVisibleItemPosition + 2)
                            }
                        }
                    }
                    return null
                }
            }
            is LinearLayoutManager -> {
                with(layoutManager) {
                    val firstVisibleItemPosition = findFirstVisibleItemPosition()
                    val isLastItem = findLastCompletelyVisibleItemPosition() == itemCount - 1
                    if (firstVisibleItemPosition == RecyclerView.NO_POSITION || isLastItem) {
                        return null
                    }
                    val child = findViewByPosition(firstVisibleItemPosition)
                    return if (helper.getDecoratedEnd(child) >= helper.getDecoratedMeasurement(child) / 2
                        && helper.getDecoratedEnd(child) > 0
                    ) {
                        child
                    } else {
                        if (findLastCompletelyVisibleItemPosition() == itemCount - 1) {
                            null
                        } else {
                            findViewByPosition(firstVisibleItemPosition + 1)
                        }
                    }
                }
            }
            else -> {
                throw IllegalArgumentException("UnSupport layout manager: $layoutManager")
            }
        }

    private fun distanceToStart(targetView: View, helper: OrientationHelper) =
        helper.getDecoratedStart(targetView) - helper.startAfterPadding

    private fun getHorizontalHelper(layoutManager: RecyclerView.LayoutManager): OrientationHelper {
        if (horizontalHelper == null) {
            horizontalHelper = OrientationHelper.createHorizontalHelper(layoutManager)
        }
        return horizontalHelper!!
    }

    private fun getVerticalHelper(layoutManager: RecyclerView.LayoutManager): OrientationHelper {
        if (verticalHelper == null) {
            verticalHelper = OrientationHelper.createVerticalHelper(layoutManager)
        }
        return verticalHelper!!
    }
}

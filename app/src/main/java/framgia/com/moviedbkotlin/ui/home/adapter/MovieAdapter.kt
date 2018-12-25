package framgia.com.moviedbkotlin.ui.home.adapter

import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import framgia.com.moviedbkotlin.R
import framgia.com.moviedbkotlin.data.Movie
import framgia.com.moviedbkotlin.repository.NetworkState
import framgia.com.moviedbkotlin.ui.home.HomeActionListener
import java.util.concurrent.Executors

/**
 * Created: 17/12/2018
 * By: Sang
 * Description:
 */
class MovieAdapter(
    private val actionListener: HomeActionListener
) : PagedListAdapter<Movie, RecyclerView.ViewHolder>(diffUtilCallback) {

    private var networkState: NetworkState? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        when (viewType) {
            R.layout.item_network_state -> {
                MovieNetworkStateViewHolder.create(parent, actionListener)
            }
            R.layout.item_movie -> {
                MovieViewHolder.create(parent, actionListener)
            }
            R.layout.item_first_movie -> {
                FirstPopularMovieViewHolder.create(parent, actionListener)
            }
            else -> {
                throw IllegalArgumentException("Unknown view type: $viewType")
            }
        }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            R.layout.item_movie -> {
                (holder as MovieViewHolder).bindView(getItem(position) ?: Movie())
            }
            R.layout.item_first_movie -> {
                (holder as FirstPopularMovieViewHolder).bindView(getItem(position) ?: Movie())
            }
            R.layout.item_network_state -> {
                (holder as MovieNetworkStateViewHolder).bindView()
            }
            else -> {
                throw IllegalArgumentException("Unknown view type: ${getItemViewType(position)}")
            }
        }
    }

    override fun getItemViewType(position: Int): Int =
        if (hasExtraRow() && position == itemCount - 1) {
            R.layout.item_network_state
        } else {
            if (position == 0) {
                R.layout.item_first_movie
            } else {
                R.layout.item_movie
            }
        }

    override fun getItemCount(): Int = super.getItemCount() + if (hasExtraRow()) 1 else 0

    private fun hasExtraRow() =
        networkState != null && networkState != NetworkState.SUCCESS && networkState != NetworkState.RUNNING

    fun setNetworkState(newNetworkState: NetworkState?) {
        val previousState = networkState
        val hadExtraRow = hasExtraRow()
        networkState = newNetworkState
        val hasExtraRow = hasExtraRow()
        if (hadExtraRow != hasExtraRow) {
            if (hasExtraRow) {
                // We add retry_item to the end of list because now, we don't have network.
                notifyItemInserted(super.getItemCount())
            } else {
                // We currently have retry_item in end of list. Now, load success so remove it
                notifyItemRemoved(itemCount - 1)
            }
        } else if (hasExtraRow && previousState != newNetworkState) {
            // newNetworkState is Running or Failed, previousState is SUCCESS
            notifyItemChanged(itemCount - 1)
        }
    }

    companion object {

        private val POPULAR_MOVIE_COMPARATOR = object : DiffUtil.ItemCallback<Movie>() {

            override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean =
                oldItem.title == newItem.title
                        && oldItem.voteAverage == newItem.voteAverage
                        && oldItem.releaseDate == newItem.releaseDate
        }

        val diffUtilCallback = AsyncDifferConfig.Builder<Movie>(POPULAR_MOVIE_COMPARATOR)
            .setBackgroundThreadExecutor(Executors.newSingleThreadExecutor())
            .build()
    }
}

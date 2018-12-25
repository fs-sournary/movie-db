package framgia.com.moviedbkotlin.ui.home

import android.util.Log
import androidx.databinding.BindingAdapter
import androidx.paging.PagedList
import androidx.recyclerview.widget.RecyclerView
import framgia.com.moviedbkotlin.data.Movie
import framgia.com.moviedbkotlin.ui.home.adapter.MovieAdapter

/**
 * Created: 24/12/2018
 * By: Sang
 * Description:
 */
private const val TAG = "HomeBindings_tag"

@BindingAdapter("movieItems")
fun RecyclerView.setMovies(items: PagedList<Movie>?) {
    items?.let {
        when (adapter) {
            is MovieAdapter -> (adapter as MovieAdapter).submitList(it)
            else -> Log.d(TAG, "Not support this adapter: ${adapter.toString()}")
        }
    }
}

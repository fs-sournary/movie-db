package framgia.com.moviedbkotlin.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import framgia.com.moviedbkotlin.data.Movie
import framgia.com.moviedbkotlin.databinding.ItemPopularMovieBinding
import framgia.com.moviedbkotlin.ui.home.HomeActionListener

/**
 * Created: 17/12/2018
 * By: Sang
 * Description:
 */
class MovieViewHolder(
    private val binding: ItemPopularMovieBinding,
    private val listener: HomeActionListener
) : RecyclerView.ViewHolder(binding.root) {

    fun bindView(item: Movie) {
        binding.apply {
            movie = item
            userActionListener = listener
            executePendingBindings()
        }
    }

    companion object {

        fun create(
            parent: ViewGroup,
            listener: HomeActionListener
        ): MovieViewHolder {
            val binding =
                ItemPopularMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return MovieViewHolder(binding, listener)
        }
    }
}

package framgia.com.moviedbkotlin.ui.home.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import framgia.com.moviedbkotlin.data.Movie
import framgia.com.moviedbkotlin.databinding.ItemMovieBinding
import framgia.com.moviedbkotlin.ui.home.HomeActionListener

/**
 * Created: 17/12/2018
 * By: Sang
 * Description:
 */
class MovieViewHolder(
    private val binding: ItemMovieBinding,
    private val listener: HomeActionListener
) : RecyclerView.ViewHolder(binding.root) {

    private val circlePlaceHolder = CircularProgressDrawable(binding.root.context).apply {
        strokeWidth = 5f
        centerRadius = 30f
        setColorSchemeColors(Color.WHITE)
        start()
    }

    fun bindView(item: Movie) {
        binding.apply {
            movie = item
            placeHolder = circlePlaceHolder
            userActionListener = listener
            executePendingBindings()
        }
    }

    companion object {

        fun create(parent: ViewGroup, listener: HomeActionListener): MovieViewHolder {
            val binding =
                ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return MovieViewHolder(binding, listener)
        }
    }
}

package framgia.com.moviedbkotlin.ui.home.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import framgia.com.moviedbkotlin.data.Movie
import framgia.com.moviedbkotlin.databinding.ItemFirstMovieBinding
import framgia.com.moviedbkotlin.ui.home.MovieListener

/**
 * Created: 17/12/2018
 * By: Sang
 * Description:
 */
class FirstMovieViewHolder(
    private val binding: ItemFirstMovieBinding,
    private val listener: MovieListener
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

        fun create(
            parent: ViewGroup,
            listener: MovieListener
        ): FirstMovieViewHolder {
            val binding = ItemFirstMovieBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
            return FirstMovieViewHolder(binding, listener)
        }
    }
}

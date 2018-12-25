package framgia.com.moviedbkotlin.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import framgia.com.moviedbkotlin.databinding.ItemNetworkStateBinding
import framgia.com.moviedbkotlin.ui.home.MovieListener

/**
 * Created: 17/12/2018
 * By: Sang
 * Description:
 */
class MovieNetworkStateViewHolder(
    private val binding: ItemNetworkStateBinding,
    private val listener: MovieListener
) : RecyclerView.ViewHolder(binding.root) {

    fun bindView() {
        binding.apply {
            userActionsListener = listener
            executePendingBindings()
        }
    }

    companion object {

        fun create(parent: ViewGroup, listener: MovieListener): MovieNetworkStateViewHolder {
            val binding = ItemNetworkStateBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
            return MovieNetworkStateViewHolder(binding, listener)
        }
    }
}

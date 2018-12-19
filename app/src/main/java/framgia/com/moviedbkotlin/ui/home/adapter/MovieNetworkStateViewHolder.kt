package framgia.com.moviedbkotlin.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import framgia.com.moviedbkotlin.databinding.ItemPopularNetworkStateBinding
import framgia.com.moviedbkotlin.ui.home.HomeActionListener

/**
 * Created: 17/12/2018
 * By: Sang
 * Description:
 */
class MovieNetworkStateViewHolder(
    private val binding: ItemPopularNetworkStateBinding,
    private val listener: HomeActionListener
) : RecyclerView.ViewHolder(binding.root) {

    fun bindView() {
        binding.apply {
            userActionsListener = listener
            executePendingBindings()
        }
    }

    companion object {

        fun create(
            parent: ViewGroup,
            listener: HomeActionListener
        ): MovieNetworkStateViewHolder {
            val binding = ItemPopularNetworkStateBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
            return MovieNetworkStateViewHolder(binding, listener)
        }
    }
}

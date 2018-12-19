package framgia.com.moviedbkotlin.ui.home.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import framgia.com.moviedbkotlin.databinding.ItemGeneralGenreBinding
import framgia.com.moviedbkotlin.ui.home.GeneralGenreActionListener

/**
 * Created: 18/12/2018
 * By: Sang
 * Description:
 */
class GeneralGenreViewHolder(
    private val binding: ItemGeneralGenreBinding,
    private val generalGenreActionListener: GeneralGenreActionListener
) : RecyclerView.ViewHolder(binding.root) {

    fun bindView(item: String) {
        binding.apply {
            general = item
            listener = generalGenreActionListener
            executePendingBindings()
        }
    }

    fun hideDecorator() {
        binding.separator.visibility = View.GONE
    }

    companion object {

        fun create(
            parent: ViewGroup,
            listener: GeneralGenreActionListener
        ): GeneralGenreViewHolder {
            val binding =
                ItemGeneralGenreBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return GeneralGenreViewHolder(binding, listener)
        }
    }
}

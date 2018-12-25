package framgia.com.moviedbkotlin.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import framgia.com.moviedbkotlin.databinding.ItemCategoryBinding
import framgia.com.moviedbkotlin.ui.home.CategoryListener

/**
 * Created: 18/12/2018
 * By: Sang
 * Description:
 */
class CategoryViewHolder(
    private val binding: ItemCategoryBinding,
    private val categoryListener: CategoryListener
) : RecyclerView.ViewHolder(binding.root) {

    var isSeparatorVisible = false

    fun bindView(item: String) {
        binding.apply {
            general = item
            separatorVisibility = isSeparatorVisible
            position = adapterPosition
            listener = categoryListener
            executePendingBindings()
        }
    }

    companion object {

        fun create(
            parent: ViewGroup, listener: CategoryListener
        ): CategoryViewHolder {
            val binding =
                ItemCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return CategoryViewHolder(binding, listener)
        }
    }
}

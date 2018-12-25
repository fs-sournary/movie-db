package framgia.com.moviedbkotlin.ui.home.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import framgia.com.moviedbkotlin.ui.home.CategoryListener

/**
 * Created: 18/12/2018
 * By: Sang
 * Description:
 */
class CategoryAdapter(private val generalGenreListener: CategoryListener) :
    ListAdapter<String, CategoryViewHolder>(GENERAL_GENRE_COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder =
        CategoryViewHolder.create(parent, generalGenreListener)

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.isSeparatorVisible = position != itemCount - 1
        holder.bindView(getItem(position))
    }

    companion object {

        val GENERAL_GENRE_COMPARATOR = object : DiffUtil.ItemCallback<String>() {
            override fun areItemsTheSame(oldItem: String, newItem: String): Boolean =
                oldItem == newItem

            override fun areContentsTheSame(oldItem: String, newItem: String): Boolean =
                oldItem == newItem
        }
    }
}
package framgia.com.moviedbkotlin.ui.home.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import framgia.com.moviedbkotlin.ui.home.GeneralGenreActionListener

/**
 * Created: 18/12/2018
 * By: Sang
 * Description:
 */
class GeneralGenreAdapter(private val listener: GeneralGenreActionListener) :
    ListAdapter<String, GeneralGenreViewHolder>(GENERAL_GENRE_COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GeneralGenreViewHolder =
        GeneralGenreViewHolder.create(parent, listener)

    override fun onBindViewHolder(holder: GeneralGenreViewHolder, position: Int) {
        holder.bindView(getItem(position))
        if (position == itemCount - 1){
            holder.hideDecorator()
        }
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
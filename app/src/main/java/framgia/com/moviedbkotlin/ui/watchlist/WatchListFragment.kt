package framgia.com.moviedbkotlin.ui.watchlist

import framgia.com.moviedbkotlin.R
import framgia.com.moviedbkotlin.databinding.FragmentWatchListBinding
import framgia.com.moviedbkotlin.ui.BaseFragment
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

/**
 * Created: 12/18/18.
 * By: Sang
 * Description:
 */
class WatchListFragment : BaseFragment<WatchListViewModel, FragmentWatchListBinding>() {

    override val viewModel: WatchListViewModel by sharedViewModel()

    override val layoutId: Int = R.layout.fragment_watch_list
}

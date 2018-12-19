package framgia.com.moviedbkotlin.ui.library

import framgia.com.moviedbkotlin.R
import framgia.com.moviedbkotlin.databinding.FragmentLibraryBinding
import framgia.com.moviedbkotlin.ui.BaseFragment
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

/**
 * Created: 12/18/18.
 * By: Sang
 * Description:
 */
class LibraryFragment : BaseFragment<LibraryViewModel, FragmentLibraryBinding>() {

    override val viewModel: LibraryViewModel by sharedViewModel()

    override val layoutId: Int = R.layout.fragment_library
}

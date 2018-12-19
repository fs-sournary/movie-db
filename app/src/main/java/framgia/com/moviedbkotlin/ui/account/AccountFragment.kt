package framgia.com.moviedbkotlin.ui.account

import framgia.com.moviedbkotlin.R
import framgia.com.moviedbkotlin.databinding.FragmentAccountBinding
import framgia.com.moviedbkotlin.ui.BaseFragment
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

/**
 * Created: 12/18/18.
 * By: Sang
 * Description:
 */
class AccountFragment : BaseFragment<AccountViewModel, FragmentAccountBinding>() {

    override val viewModel: AccountViewModel by sharedViewModel()

    override val layoutId: Int = R.layout.fragment_account
}

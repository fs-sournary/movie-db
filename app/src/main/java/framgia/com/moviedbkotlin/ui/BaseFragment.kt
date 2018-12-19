package framgia.com.moviedbkotlin.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import framgia.com.moviedbkotlin.BR

/**
 * Created: 12/16/18.
 * By: Sang
 * Description:
 */
abstract class BaseFragment<VM : ViewModel, Binding : ViewDataBinding> : Fragment() {

    lateinit var binding: Binding
    abstract val viewModel: VM

    @get:LayoutRes
    abstract val layoutId: Int

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, layoutId, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            setVariable(BR.viewModel, viewModel)
            setLifecycleOwner(viewLifecycleOwner)
            executePendingBindings()
        }
    }
}

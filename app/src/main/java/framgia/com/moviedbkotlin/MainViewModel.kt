package framgia.com.moviedbkotlin

import androidx.lifecycle.ViewModel
import framgia.com.moviedbkotlin.util.SingleLiveEvent

/**
 * Created: 12/16/18.
 * By: Sang
 * Description:
 */
class MainViewModel : ViewModel() {

    val onBackPressEvent = SingleLiveEvent<Unit>()
}

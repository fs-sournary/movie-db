package framgia.com.moviedbkotlin

import android.os.Build
import android.os.Bundle
import android.text.Html
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * Created: 12/15/18.
 * By: Sang
 * Description:
 */
class MainActivity : AppCompatActivity() {

    private val mainViewModel: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupToolbar()
        setupBottomNav()
    }

    private fun setupToolbar(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            toolbar.title = Html.fromHtml(
                getString(R.string.toolbar_main_title),
                Html.FROM_HTML_MODE_COMPACT
            )
        } else {
            toolbar.title = Html.fromHtml(getString(R.string.toolbar_main_title))
        }
    }

    private fun setupBottomNav() {
        val navController = findNavController(R.id.fragment_host_main)
        bottom_nav.setupWithNavController(navController)
    }

    override fun onBackPressed() {
        mainViewModel.onBackPressEvent.call()
    }
}

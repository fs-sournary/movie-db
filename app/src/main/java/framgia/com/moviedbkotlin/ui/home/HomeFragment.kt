package framgia.com.moviedbkotlin.ui.home

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import framgia.com.moviedbkotlin.MainViewModel
import framgia.com.moviedbkotlin.R
import framgia.com.moviedbkotlin.data.Movie
import framgia.com.moviedbkotlin.databinding.FragmentHomeBinding
import framgia.com.moviedbkotlin.ui.BaseFragment
import framgia.com.moviedbkotlin.ui.home.adapter.GeneralGenreAdapter
import framgia.com.moviedbkotlin.ui.home.adapter.MovieAdapter
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

/**
 * Created: 12/15/18.
 * By: Sang
 * Description:
 */
class HomeFragment :
    BaseFragment<HomeViewModel, FragmentHomeBinding>(),
    HomeNavigator {

    private lateinit var mMovieAdapter: MovieAdapter

    private val mainViewModel: MainViewModel by sharedViewModel()
    override val viewModel: HomeViewModel by sharedViewModel()

    override val layoutId: Int = R.layout.fragment_home

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupPopularMovieList()
        setupGeneralGenreList()
        setupViewModel()
    }

    private fun setupPopularMovieList() {
        mMovieAdapter = MovieAdapter(object : HomeActionListener {

            override fun retry() {

            }

            override fun clickPopularMovieItem(movie: Movie) {

            }
        })
        binding.recyclerMovie.apply {
            adapter = mMovieAdapter
            // Fragment is destroyed but viewModel is attached with Activity.
            // Fragment is created, scroll to previous position with previous movies
            scrollToPosition(viewModel.popularMovieScrollPosition)
            with(layoutManager as GridLayoutManager) {
                spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                    override fun getSpanSize(position: Int): Int = if (position == 0) 2 else 1
                }
            }
            // StartSnapHelper().attachToRecyclerView(this)
        }
    }

    private fun setupGeneralGenreList() {
        val generalGenreActionListener = object : GeneralGenreActionListener {

            override fun clickToGeneralGenre(value: String) {

            }
        }
        val generalGenreAdapter = GeneralGenreAdapter(generalGenreActionListener)
        binding.recyclerGeneralMovieGenre.adapter = generalGenreAdapter
        // Get general genre from resource. Later, we can do multi language.
        val generalGenres = listOf(
            activity!!.getString(R.string.popular),
            activity!!.getString(R.string.top_rate),
            activity!!.getString(R.string.now_playing),
            activity!!.getString(R.string.upcoming)
        )
        generalGenreAdapter.submitList(generalGenres)
    }

    private fun setupViewModel() {
        mainViewModel.onBackPressEvent.observe(viewLifecycleOwner, Observer {
            finishApp()
        })
        viewModel.apply {
            // ViewModel call init when setVariable in onViewCreated() before adapter is initialized
            popularMovies.observe(viewLifecycleOwner, Observer {
                mMovieAdapter.submitList(it)
            })
            networkState.observe(viewLifecycleOwner, Observer {
                mMovieAdapter.setNetworkState(it)
            })
        }
    }

    override fun onPause() {
        super.onPause()
        with(binding.recyclerMovie.layoutManager as GridLayoutManager) {
            viewModel.popularMovieScrollPosition = findFirstVisibleItemPosition()
        }
    }

    override fun finishApp() {
        activity?.finish()
    }
}

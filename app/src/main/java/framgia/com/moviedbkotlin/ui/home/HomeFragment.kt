package framgia.com.moviedbkotlin.ui.home

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import framgia.com.moviedbkotlin.MainViewModel
import framgia.com.moviedbkotlin.R
import framgia.com.moviedbkotlin.data.Movie
import framgia.com.moviedbkotlin.databinding.FragmentHomeBinding
import framgia.com.moviedbkotlin.ui.BaseFragment
import framgia.com.moviedbkotlin.ui.home.adapter.CategoryAdapter
import framgia.com.moviedbkotlin.ui.home.adapter.MovieAdapter
import framgia.com.moviedbkotlin.widget.StartSnapHelper
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

/**
 * Created: 12/15/18.
 * By: Sang
 * Description:
 */
class HomeFragment :
    BaseFragment<HomeViewModel, FragmentHomeBinding>(),
    HomeNavigator {

    private val mainViewModel: MainViewModel by sharedViewModel()
    override val viewModel: HomeViewModel by sharedViewModel()

    private lateinit var movieAdapter: MovieAdapter

    override val layoutId: Int = R.layout.fragment_home

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupPopularMovieList()
        setupGeneralGenreList()
        setupViewModel()
    }

    override fun onStart() {
        super.onStart()
        Log.d("HomeFragment_test", "onStart")
    }

    override fun onStop() {
        super.onStop()
        Log.d("HomeFragment_test", "onStop")
    }

    private fun setupPopularMovieList() {
        movieAdapter = MovieAdapter(object : MovieListener {
            override fun retry() {

            }

            override fun onClickMovie(movie: Movie) {

            }
        })
        binding.recyclerMovie.apply {
            adapter = movieAdapter
            // Fragment is destroyed but viewModel is attached with Activity.
            // Fragment is created, scroll to previous position with previous movies
            with(layoutManager as GridLayoutManager) {
                spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                    override fun getSpanSize(position: Int): Int =
                        if (position == 0) SPAN_SIZE_ONE_COUNT else SPAN_SIZE_TWO_COUNT
                }
            }
            scrollToPosition(viewModel.movieScrollPosition)
            StartSnapHelper().attachToRecyclerView(this)
        }
    }

    private fun setupGeneralGenreList() {
        val listener = object : CategoryListener {
            override fun onClickGeneralGenre(position: Int) {
                when (position) {
                    0 -> viewModel.loadPopularMovies()
                    1 -> viewModel.loadTopRateMovies()
                    2 -> viewModel.loadNowPlayingMovies()
                    3 -> viewModel.loadUpcomingMovies()
                    else -> throw IllegalArgumentException("Unknown position: $position")
                }
            }
        }
        val generalGenreAdapter = CategoryAdapter(listener)
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
            movies.observe(viewLifecycleOwner, Observer {
                movieAdapter.submitList(it)
            })
            movieNetworkState.observe(viewLifecycleOwner, Observer {
                movieAdapter.setNetworkState(it)
            })
//            loadPopularMovies()
            loadGenres()
        }
    }

    override fun onPause() {
        super.onPause()
        with(binding.recyclerMovie.layoutManager as GridLayoutManager) {
            viewModel.movieScrollPosition = findFirstVisibleItemPosition()
        }
    }

    override fun finishApp() {
        activity?.finish()
    }

    companion object {

        private const val SPAN_SIZE_TWO_COUNT = 1
        private const val SPAN_SIZE_ONE_COUNT = 2
    }
}

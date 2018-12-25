package framgia.com.moviedbkotlin.ui.moviedetail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import framgia.com.moviedbkotlin.R
import framgia.com.moviedbkotlin.databinding.FragmentMovieDetailBinding
import framgia.com.moviedbkotlin.ui.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * Created: 21/12/2018
 * By: Sang
 * Description:
 */
//class MovieDetailFragment : BaseFragment<MovieDetailViewModel, FragmentMovieDetailBinding>() {
//
//    override val viewModel: MovieDetailViewModel by viewModel()
//
//    override val layoutId: Int = R.layout.fragment_movie_detail
//
//    override fun onActivityCreated(savedInstanceState: Bundle?) {
//        super.onActivityCreated(savedInstanceState)
//    }
//}

class MovieDetailFragment: AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_movie_detail)
    }
}

package framgia.com.moviedbkotlin.db

import androidx.room.Dao
import androidx.room.Query
import framgia.com.moviedbkotlin.data.Movie
import io.reactivex.Single

/**
 * Created: 18/07/2018
 * By: Sang
 * Description:
 */
@Dao
interface MovieDao : BaseDao<Movie> {

    @Query("SELECT * FROM movie")
    fun getLocalMovies(): Single<List<Movie>>

    @Query("SELECT * FROM movie where movie.id = :movieId")
    fun getMovieById(movieId: Int): Single<Movie>
}

package framgia.com.moviedbkotlin.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import framgia.com.moviedbkotlin.data.Movie

/**
 * Created: 18/07/2018
 * By: Sang
 * Description:
 */

@Database(entities = [(Movie::class)], version = 1)
abstract class MovieDB : RoomDatabase() {

    abstract fun getMovieDao(): MovieDao

    companion object {

        private const val DATABASE_NAME = "localmoviedb.db"

        @Synchronized
        fun newInstance(context: Context): MovieDB =
            Room.databaseBuilder(context, MovieDB::class.java, DATABASE_NAME).build()
    }
}

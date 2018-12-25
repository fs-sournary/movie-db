package framgia.com.moviedbkotlin.data

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

/**
 * Created: 09/07/2018
 * By: Sang
 * Description:
 */

@Parcelize
@Entity(tableName = "movie")
data class Movie(
    @SerializedName("id")
    @PrimaryKey(autoGenerate = false)
    var id: Int = 0,
    @SerializedName("imdb_id")
    var imdbId: Int? = null,
    @SerializedName("adult")
    var adult: Boolean? = null,
    @SerializedName("backdrop_path")
    var backdropPath: String? = null,
    @SerializedName("poster_path")
    var posterPath: String? = null,
    @SerializedName("budget")
    var budget: Int? = null,
    @SerializedName("_genres")
    @Ignore
    var genres: List<Genre>? = null,
    @SerializedName("homepage")
    var homepage: String? = null,
    @SerializedName("original_language")
    var originalLanguage: String? = null,
    @SerializedName("original_title")
    var originalTitle: String? = null,
    @SerializedName("overview")
    var overview: String? = null,
    @SerializedName("popularity")
    var popularity: Double? = null,
    @SerializedName("production_companies")
    @Ignore
    var productionCompanies: List<ProductionCompany>? = null,
    @SerializedName("production_countries")
    @Ignore
    var productionCountries: List<ProductionCountry>? = null,
    @SerializedName("release_date")
    var releaseDate: String? = null,
    @SerializedName("revenue")
    var revenue: Int? = null,
    @SerializedName("runtime")
    var runtime: Int? = null,
    @SerializedName("spoken_languages")
    @Ignore
    var spokenLanguages: List<SpokenLanguage>? = listOf(),
    @SerializedName("status")
    var status: String? = null,
    @SerializedName("tagline")
    var tagLine: String? = null,
    @SerializedName("title")
    var title: String? = null,
    @SerializedName("video")
    var video: Boolean? = null,
    @SerializedName("vote_average")
    var voteAverage: Double? = null,
    @SerializedName("vote_count")
    var voteCount: Int? = null
) : Parcelable

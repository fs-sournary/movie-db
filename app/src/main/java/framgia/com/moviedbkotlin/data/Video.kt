package framgia.com.moviedbkotlin.data

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

/**
 * Created by fs-sournary.
 * Date: 7/19/18.
 * Description:
 */
@Parcelize
data class Video(
    var id: String,
    @SerializedName("iso_639_1")
    var iso6391: String? = null,
    @SerializedName("iso_3166_1")
    var iso31661: String? = null,
    var key: String? = null,
    var name: String? = null,
    var site: String? = null,
    var size: Int? = null,
    var type: String? = null
) : Parcelable

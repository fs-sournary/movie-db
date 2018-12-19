package framgia.com.moviedbkotlin.data

import android.os.Parcelable
import androidx.room.Entity
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

/**
 * Created: 11/07/2018
 * By: Sang
 * Description:
 */

@Parcelize
@Entity(tableName = "production_country")
data class ProductionCountry(
    @SerializedName("iso_3166_1")
    val iso31661: String? = null,
    @SerializedName("name")
    val name: String? = null
) : Parcelable

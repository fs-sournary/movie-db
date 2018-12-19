package framgia.com.moviedbkotlin.data

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

/**
 * Created: 11/07/2018
 * By: Sang
 * Description:
 */

@Parcelize
@Entity(tableName = "production_company")
class ProductionCompany(
    @SerializedName("id")
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    @SerializedName("logo_path")
    val logoPath: String? = null,
    @SerializedName("origin_country")
    val originCountry: String? = null
) : Parcelable

package framgia.com.moviedbkotlin.data

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

/**
 * Created: 16/07/2018
 * By: Sang
 * Description:
 */

@Parcelize
data class Cast(
    @SerializedName("cast_id")
    val castId: Int,
    @SerializedName("character")
    val character: String? = null,
    @SerializedName("credit_id")
    val creditId: String? = null,
    @SerializedName("gender")
    val gender: Int? = null,
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String? = null,
    @SerializedName("order")
    val order: Int? = null,
    @SerializedName("profile_path")
    val profilePath: String? = null
) : Parcelable

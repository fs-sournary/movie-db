package framgia.com.moviedbkotlin.data

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

/**
 * Created by fs-sournary.
 * Date: 7/10/18.
 * Description:
 */

@Parcelize
@Entity(tableName = "genre")
class Genre(
    @SerializedName("id")
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    @SerializedName("name")
    val name: String? = null
) : Parcelable

package framgia.com.moviedbkotlin.api

import com.google.gson.annotations.SerializedName

/**
 * Created by fs-sournary.
 * Date: 7/11/18.
 * Description:
 */
class ListResponse<Item> {
    @SerializedName("page")
    var page: Int = 0
    @SerializedName("total_results")
    var totalResult: Int = 0
    @SerializedName("total_pages")
    var totalPages: Int = 0
    @SerializedName("results")
    var results: List<Item> = listOf()
}

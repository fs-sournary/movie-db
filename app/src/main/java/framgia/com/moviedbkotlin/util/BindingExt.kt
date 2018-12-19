package framgia.com.moviedbkotlin.util

import android.annotation.SuppressLint
import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

/**
 * Created: 10/07/2018
 * By: Sang
 * Description:
 */
@SuppressLint("CheckResult")
@BindingAdapter(
    value = ["imageUrl", "placeHolder", "centerCrop", "circleCrop"],
    requireAll = false
)
fun ImageView.loadImageNetwork(
    imageUrl: String,
    placeholder: Drawable?,
    isCenterCrop: Boolean?,
    isCircleCrop: Boolean?
) {
    val requestBuilder = Glide.with(context).load(imageUrl)
    val requestOption = RequestOptions().placeholder(placeholder)
    if (isCenterCrop != null && isCenterCrop) {
        requestOption.centerCrop()
    }
    if (isCircleCrop != null && isCircleCrop) {
        requestOption.circleCrop()
    }
    requestBuilder.apply(requestOption).into(this)
}

@SuppressLint("CheckResult")
@BindingAdapter(value = ["imageLocal", "centerCrop", "circleCrop"], requireAll = false)
fun ImageView.loadImageLocal(
    imageLocal: Drawable,
    isCenterCrop: Boolean?,
    isCircleCrop: Boolean?
) {
    val requestBuilder = Glide.with(context).load(imageLocal)
    val requestOptions = RequestOptions()
    if (isCenterCrop != null && isCenterCrop) {
        requestOptions.centerCrop()
    }
    if (isCircleCrop != null && isCircleCrop) {
        requestOptions.circleCrop()
    }
    requestBuilder.apply(requestOptions).into(this)
}

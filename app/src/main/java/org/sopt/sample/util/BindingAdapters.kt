package org.sopt.sample.util

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import coil.load
import coil.transform.RoundedCornersTransformation
import org.sopt.sample.R

@BindingAdapter("app:imageUrl")
fun ImageView.loadCircleImage(imageUrl: String?) {
    load(imageUrl) {
        placeholder(R.drawable.my_profile)
            .transformations(RoundedCornersTransformation(50f))
            .crossfade(true)
            .error(R.drawable.ic_error_24)
    }
}

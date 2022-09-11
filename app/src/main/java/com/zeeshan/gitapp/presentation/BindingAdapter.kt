package com.zeeshan.gitapp.presentation

import android.net.Uri
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.zeeshan.gitapp.R


@BindingAdapter("user_avatar")
fun setUserAvatar(imgView: ImageView, imageUri: String?){

    Glide.with(imgView.context)
        .load(Uri.parse(imageUri))
        .diskCacheStrategy(DiskCacheStrategy.NONE)
        .skipMemoryCache(false)
        .dontAnimate()
        .dontTransform()
        .apply(RequestOptions().override(200, 200))
        .placeholder(R.drawable.ic_git_icon)
        .error(R.drawable.ic_git_icon)
        .into(imgView)

}
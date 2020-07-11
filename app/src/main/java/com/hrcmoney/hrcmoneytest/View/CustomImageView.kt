package com.hrcmoney.hrcmoneytest.View

import android.content.Context
import android.graphics.Bitmap
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView
import com.hrcmoney.hrcmoneytest.Config.DownloadImageTask
import com.hrcmoney.hrcmoneytest.Config.ImageManageHelper

class CustomImageView : AppCompatImageView {

    var urlCheck: String = ""

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(
        context,
        attrs,
        defStyle
    ) {
    }

    constructor(context: Context, attrs: AttributeSet) : this(context, attrs, 0) {}

//    fun downloadImg(url: String, imageView: AppCompatImageView) {
//        urlCheck = url
//        val nUrl = ImageManageHelper().agentAdjustWithThumbnailUrl(url)
//        DownloadImageTask(0).execute(nUrl)
//
//    }
}
package com.hrcmoney.hrcmoneytest.Config

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.AsyncTask
import android.widget.ImageView
import com.hrcmoney.hrcmoneytest.Config.ImageManageHelper.Companion.imageStorage
import java.io.InputStream
import java.net.URL

class DownloadImageTask(private val bmImage: ImageView, private val save: Boolean) :
    AsyncTask<String?, Void?, Bitmap?>() {

    override fun doInBackground(vararg params: String?): Bitmap? {
        val urlDisplay = params[0]
        var mIcon: Bitmap? = null
        if (!isCancelled) {
            try {
                val `in`: InputStream = URL(urlDisplay).openStream()
                mIcon = BitmapFactory.decodeStream(`in`)
                if (save && mIcon != null)
                    imageStorage.add(mIcon)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        return mIcon
    }

    override fun onPostExecute(result: Bitmap?) {
        bmImage.setImageBitmap(result)
    }
}
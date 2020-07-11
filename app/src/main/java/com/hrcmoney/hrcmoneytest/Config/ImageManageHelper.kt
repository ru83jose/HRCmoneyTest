package com.hrcmoney.hrcmoneytest.Config

import android.graphics.Bitmap
import android.graphics.Color

class ImageManageHelper {
    companion object {
        val imageStorage = arrayListOf<Bitmap>()
//        val asyncStorage = arrayListOf<AsyncTask<String?, Void?, Bitmap?>>()
    }

    private val loadApiUrl = "https://ipsumimage.appspot.com/"
    private val fullLenght = 38
    private val subLenght = 28

    fun agentAdjustWithThumbnailUrl(url: String): String {
        return if (url.length < fullLenght) {
            var addString = ""
            for (i in 0 until fullLenght - url.length) {
                addString += "f"
            }
            loadApiUrl + url.substring(subLenght, url.lastIndex + 1).replace("/", ",") + addString
        } else {
            loadApiUrl + url.substring(subLenght, url.lastIndex + 1).replace("/", ",")
        }
    }

    fun isColorDark(colorHex: String): Boolean {

        val color = Color.parseColor(colorHex)

        val darkness: Double =
            1 - (0.299 * Color.red(color) + 0.587 * Color.green(color) + 0.114 * Color.blue(
                color
            )) / 255
        return darkness >= 0.823
    }

//    fun stopLoadImgTask(){
//        loopBreak = true
//        asyncStorage.forEach {
//            it.cancel(true)
//        }
//        asyncStorage.clear()
//    }
}
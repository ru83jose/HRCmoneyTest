package com.hrcmoney.hrcmoneytest.Config

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Dialog
import android.os.AsyncTask
import android.os.Build
import com.hrcmoney.hrcmoneytest.R

class ProgressBar(activity: Activity) : AsyncTask<Void, Void, Void>() {

    var finish = false

    @SuppressLint("StaticFieldLeak")
    val activ = activity
    private var dialog = Dialog(activity, android.R.style.Theme_Translucent_NoTitleBar)

    @SuppressLint("InflateParams", "ObsoleteSdkInt")
    override fun onPreExecute() {
        val view = activ.layoutInflater.inflate(R.layout.progress_bar, null)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            view.background = null
        }
        dialog.setContentView(view)
        dialog.setCancelable(false)
        dialog.show()
        super.onPreExecute()
    }

    override fun doInBackground(vararg p0: Void?): Void? {
        while (!finish) {
            Thread.sleep(100)
        }
        return null
    }

    override fun onPostExecute(result: Void?) {
        super.onPostExecute(result)
        dialog.dismiss()
    }
}
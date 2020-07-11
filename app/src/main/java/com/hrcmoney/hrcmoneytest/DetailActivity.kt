package com.hrcmoney.hrcmoneytest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.hrcmoney.hrcmoneytest.Config.DownloadImageTask
import com.hrcmoney.hrcmoneytest.Config.ImageManageHelper
import com.hrcmoney.hrcmoneytest.Config.ImageManageHelper.Companion.imageStorage
import com.hrcmoney.hrcmoneytest.Config.ProgressBar
import com.hrcmoney.hrcmoneytest.Model.AlbumParcel
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity() {
    private lateinit var detailData: AlbumParcel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        detailData = intent.getParcelableExtra(getString(R.string.listParceKey))!!
        setUpActionBar()
        setUpDetailView()
    }

    private fun setUpActionBar() {
        setSupportActionBar(findViewById(R.id.toolbar_detail))
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun setUpDetailView() {
        val progressBar = ProgressBar(this)
        progressBar.execute()
        textView_detail_id.text = detailData.id.toString()
        textView_detail_title.text = detailData.title
        val nUrl = ImageManageHelper().agentAdjustWithThumbnailUrl(detailData.thumbnailUrl)
            .replace("150", "600")
        DownloadImageTask(imageView_detail, false).execute(nUrl)
        progressBar.finish = true
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
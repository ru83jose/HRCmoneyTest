package com.hrcmoney.hrcmoneytest

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.google.gson.Gson
import com.hrcmoney.hrcmoneytest.Config.ImageManageHelper.Companion.imageStorage
import com.hrcmoney.hrcmoneytest.Config.IntentMethod.PublicIntent.startIntentParcelableActivity
import com.hrcmoney.hrcmoneytest.Config.ProgressBar
import com.hrcmoney.hrcmoneytest.Model.Album
import com.hrcmoney.hrcmoneytest.Model.AlbumAdapter
import com.hrcmoney.hrcmoneytest.Model.AlbumParcel
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.activity_list.*
import kotlinx.android.synthetic.main.cell_recyclerview_list.view.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL


class ListActivity : AppCompatActivity() {

    private val adapter = GroupAdapter<ViewHolder>()
    var jsonData = listOf<Album>()
    private val gson = Gson()
    private var cellCount = 0
    private val pageSize = 30

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)
        setUpActionBar()
        getAlbumRequest()
        setUpRecyclerView()
    }

    private fun setUpActionBar() {
        setSupportActionBar(findViewById(R.id.toolbar_list))
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun setUpRecyclerView() {
        recyclerview_list.adapter = adapter
        recyclerview_list.layoutManager =
            StaggeredGridLayoutManager(4, StaggeredGridLayoutManager.VERTICAL)

        recyclerview_list.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(1)) {
                    val progressBar = ProgressBar(this@ListActivity)
                    progressBar.execute()
                    GlobalScope.launch {
                        if (cellCount <= jsonData.size - pageSize) {
                            val last = cellCount + pageSize
                            addCellToAdapter(cellCount, last)
                        }
                        progressBar.finish = true
                    }
                }
            }
        })

        adapter.setOnItemClickListener { _, view ->
            val indexId = view.textView_cell_list_id.text.toString().toInt()
            val indexTitle = view.textView_cell_list_title.text.toString()
            startIntentParcelableActivity<DetailActivity>(
                getString(R.string.listParceKey),
                AlbumParcel(indexId, indexTitle, jsonData[indexId - 1].thumbnailUrl)
            )
        }
    }

    private fun getAlbumRequest() {
        val mURL = URL("${getString(R.string.baseApi)}/photos")

        with(mURL.openConnection() as HttpURLConnection) {
            val progressBar = ProgressBar(this@ListActivity)
            progressBar.execute()
            requestMethod = "GET"
            GlobalScope.launch {
                BufferedReader(InputStreamReader(inputStream)).use {
                    val response = StringBuffer()
                    var inputLine = it.readLine()
                    while (inputLine != null) {
                        response.append(inputLine)
                        inputLine = it.readLine()
                    }
                    val adjustString =
                        response.toString().replace("id", "aid")
                    jsonData = gson.fromJson(adjustString, Array<Album>::class.java).toList()
                    imageStorage.clear()
                    addCellToAdapter(0, pageSize)
                    it.close()
                    progressBar.finish = true
                }
            }
        }
    }

    private fun addCellToAdapter(start: Int, end: Int) {
        for (i in start..end) {
            val thisObject = jsonData[i]
            runOnUiThread {
                adapter.add(AlbumAdapter(thisObject.aid, thisObject.title, thisObject.thumbnailUrl))
            }
            cellCount++
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
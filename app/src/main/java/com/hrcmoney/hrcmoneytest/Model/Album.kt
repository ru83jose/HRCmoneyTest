package com.hrcmoney.hrcmoneytest.Model

import android.graphics.Color
import android.os.Parcelable
import com.hrcmoney.hrcmoneytest.Config.DownloadImageTask
import com.hrcmoney.hrcmoneytest.Config.ImageManageHelper
import com.hrcmoney.hrcmoneytest.Config.ImageManageHelper.Companion.imageStorage
import com.hrcmoney.hrcmoneytest.R
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.parcel.Parcelize
import kotlinx.android.synthetic.main.cell_recyclerview_list.view.*

class Album(
    val albumId: Int,
    val aid: Int,
    val title: String,
    val url: String,
    val thumbnailUrl: String
)

class AlbumAdapter(
    private val aid: Int,
    private val title: String,
    private val thumbnailUrl: String
) : Item<ViewHolder>() {

    override fun getLayout(): Int {
        return R.layout.cell_recyclerview_list
    }

    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.itemView.textView_cell_list_id.text = aid.toString()

        viewHolder.itemView.textView_cell_list_title.text = title
        val nUrl = ImageManageHelper().agentAdjustWithThumbnailUrl(thumbnailUrl)
        val colorHex = "#" + nUrl.substring(nUrl.lastIndex - 5, nUrl.lastIndex + 1)
        if (ImageManageHelper().isColorDark(colorHex)) {
            viewHolder.itemView.textView_cell_list_id.setTextColor(Color.WHITE)
            viewHolder.itemView.textView_cell_list_title.setTextColor(Color.WHITE)
        }
        if (imageStorage.size < position + 1) {
            DownloadImageTask(
                viewHolder.itemView.imageView_cell_list,
                true
            ).execute(nUrl)
        } else {
            viewHolder.itemView.imageView_cell_list.setImageBitmap(imageStorage[position])
        }
    }
}

@Parcelize
class AlbumParcel(
    val id: Int,
    val title: String,
    val thumbnailUrl: String
) : Parcelable

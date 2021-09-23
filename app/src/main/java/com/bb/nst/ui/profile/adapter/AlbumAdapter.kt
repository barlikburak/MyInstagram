package com.bb.nst.ui.profile.adapter

import android.content.Context
import android.content.res.Resources
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bb.nst.data.model.user.response.MediaDetailsResponse
import com.bb.nst.databinding.ColumnItemBinding
import com.bb.nst.utils.common.doPlaceHolder
import com.bb.nst.utils.common.downloadImage
import java.util.ArrayList

/**
 * Media Item
 * ImageView and Timestamp
 */
class AlbumAdapter(private val context: Context, private val list: ArrayList<MediaDetailsResponse>) : RecyclerView.Adapter<AlbumAdapter.AlbumViewHolder>() {
    private lateinit var bindingColumn: ColumnItemBinding

    class AlbumViewHolder(view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumViewHolder {
        bindingColumn = ColumnItemBinding.inflate(LayoutInflater.from(parent.context))
        val view = bindingColumn.root
        return AlbumAdapter.AlbumViewHolder(view)
    }

    override fun onBindViewHolder(holder: AlbumViewHolder, position: Int) {
        bindingColumn.imageView.downloadImage(list[position].media_url, doPlaceHolder(context))
        bindingColumn.imageView.minimumWidth = Resources.getSystem().getDisplayMetrics().widthPixels;
        bindingColumn.textView.text = list[position].timestamp
    }

    override fun getItemCount(): Int {
        return list.size
    }

}
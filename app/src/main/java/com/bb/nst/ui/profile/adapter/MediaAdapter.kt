package com.bb.nst.ui.profile.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bb.nst.data.model.user.response.MediaDetailsResponse
import com.bb.nst.databinding.RowItemBinding

/**
 * Album(MediaList) Item (Since it is a nested arraylist.)
 * RecyclerView
 */
class MediaAdapter(private val context: Context, private val list: ArrayList<ArrayList<MediaDetailsResponse>>) : RecyclerView.Adapter<MediaAdapter.MediaViewHolder>() {
    private lateinit var bindingRow: RowItemBinding

    class MediaViewHolder(view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MediaViewHolder {
        bindingRow = RowItemBinding.inflate(LayoutInflater.from(parent.context))
        val view = bindingRow.root
        return MediaViewHolder(view)
    }

    override fun onBindViewHolder(holder: MediaViewHolder, position: Int) {
        bindingRow.recyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        bindingRow.recyclerView.adapter = AlbumAdapter(context, list[itemCount - position - 1])
    }

    override fun getItemCount(): Int {
        return list.size
    }
}
package com.example.cameraapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class AlbumAdapter(private val albumList: List<Album>, private val onDeleteClickListener: (Album) -> Unit) :
    RecyclerView.Adapter<AlbumAdapter.AlbumViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_album, parent, false)
        return AlbumViewHolder(view)
    }

    override fun onBindViewHolder(holder: AlbumViewHolder, position: Int) {
        val album = albumList[position]
        holder.albumName.text = album.name
        holder.albumDate.text = album.date

        holder.deleteButton.setOnClickListener {
            onDeleteClickListener(album)
        }
    }

    override fun getItemCount(): Int = albumList.size

    class AlbumViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val albumName: TextView = itemView.findViewById(R.id.album_name)
        val albumDate: TextView = itemView.findViewById(R.id.album_date)
        val deleteButton: Button = itemView.findViewById(R.id.delete_album)
    }
}

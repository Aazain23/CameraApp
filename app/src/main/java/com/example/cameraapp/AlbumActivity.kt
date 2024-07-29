package com.example.cameraapp

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.core.content.IntentCompat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AlbumActivity : AppCompatActivity() {

    private lateinit var albumTextView: TextView
    private lateinit var photoRecyclerView: RecyclerView
    private lateinit var photoAdapter: PhotoAdapter
    private val photoList = mutableListOf<PhotoEntity>()
    private lateinit var database: AppDatabase
    private lateinit var album: AlbumEntity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_album)

        albumTextView = findViewById(R.id.album_text_view)
        photoRecyclerView = findViewById(R.id.photo_recycler_view)
        photoRecyclerView.layoutManager = LinearLayoutManager(this)
        photoAdapter = PhotoAdapter(photoList)
        photoRecyclerView.adapter = photoAdapter

        // Initialize the database
        database = AppDatabase.getDatabase(this)

        // Retrieve the album from the intent
        album = IntentCompat.getParcelableExtra(intent, "album", AlbumEntity::class.java) ?: return

        // Set the album name
        albumTextView.text = album.name

        // Load photos from the database
        loadPhotos(album.id)
    }

    private fun loadPhotos(albumId: Int) {
        lifecycleScope.launch {
            val photos = withContext(Dispatchers.IO) {
                database.photoDao().getPhotosForAlbum(albumId)
            }
            photoList.clear()
            photoList.addAll(photos)
            photoAdapter.notifyDataSetChanged()
        }
    }
}

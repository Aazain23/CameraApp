package com.example.cameraapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class HomeActivity : AppCompatActivity() {

    private lateinit var albumRecyclerView: RecyclerView
    private lateinit var albumAdapter: AlbumAdapter
    private val albumList = mutableListOf<Album>()
    private lateinit var addAlbumButton: Button

    // Define the ActivityResultLauncher
    private val getImageResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
        if (result.resultCode == RESULT_OK) {
            val imageFilename = result.data?.getStringExtra("image")
            if (imageFilename != null) {
                addAlbum(imageFilename)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        // Initialize the RecyclerView and Adapter
        albumRecyclerView = findViewById(R.id.album_recycler_view)
        albumRecyclerView.layoutManager = LinearLayoutManager(this)

        albumAdapter = AlbumAdapter(albumList) { album ->
            // Handle album click
            val intent = Intent(this, AlbumActivity::class.java)
            intent.putExtra("album", album)
            startActivity(intent)
        }
        albumRecyclerView.adapter = albumAdapter

        // Initialize the add album button
        addAlbumButton = findViewById(R.id.add_album_button)
        addAlbumButton.setOnClickListener {
            val intent = Intent(this, CameraActivity::class.java)
            getImageResult.launch(intent)
        }

        // For testing purposes, add a sample album
        addSampleAlbum()
    }

    private fun addSampleAlbum() {
        val sampleAlbum = Album(name = "Sample Album", date = "2024-01-01", photos = mutableListOf())
        albumList.add(sampleAlbum)
        albumAdapter.notifyItemInserted(albumList.size - 1)
    }

    private fun addAlbum(imageFilename: String) {
        val newAlbum = Album(name = "New Album", date = "2024-07-28", photos = mutableListOf(imageFilename))
        albumList.add(newAlbum)
        albumAdapter.notifyItemInserted(albumList.size - 1)
    }

    //private fun deleteAlbum(album: Album) {
      //  val position = albumList.indexOf(album)
        //if (position != -1) {
          //  albumList.removeAt(position)
            //albumAdapter.notifyItemRemoved(position)
        //}
    //}
}

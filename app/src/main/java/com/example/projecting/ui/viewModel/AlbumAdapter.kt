package com.example.projecting.ui.viewModel

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.projecting.ClickOn
import com.example.projecting.data.Album
import com.example.projecting.databinding.AlbumViewBinding
import kotlinx.android.synthetic.main.album_view.view.*


class AlbumAdapter(
    var albums: List<Album>, var onPhotoClickListener: ClickOn.ClickOnPhoto
) : RecyclerView.Adapter<AlbumAdapter.AlbumViewHolder>() {

    inner class AlbumViewHolder(
        val binding: AlbumViewBinding,
        OnPhotoClickListener: ClickOn.ClickOnPhoto
    ) : RecyclerView.ViewHolder(binding.root) {

        private val onAlbumClickListener  = OnPhotoClickListener

        init {
            itemView.photobtn.setOnClickListener{
                onAlbumClickListener.clickPhoto(albums[adapterPosition].id
                ) }
        }


        fun databind(album: Album){
            binding.album = album
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = AlbumViewBinding.inflate(layoutInflater)
        return AlbumViewHolder(binding, onPhotoClickListener)
    }

    override fun getItemCount(): Int {
        return albums.size
    }

    override fun onBindViewHolder(
        holder: AlbumViewHolder, position: Int)
            = holder.databind(albums[position])

    fun updateAlbums(albums: List<Album>){
        this.albums = albums
        notifyDataSetChanged()
    }

}
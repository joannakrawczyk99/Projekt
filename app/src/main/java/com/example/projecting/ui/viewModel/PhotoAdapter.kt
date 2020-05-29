package com.example.projecting.ui.viewModel

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.projecting.R
import com.example.projecting.data.Photo
import com.example.projecting.databinding.PhotoViewBinding
import com.squareup.picasso.Picasso

class PhotoAdapter (
    var photos: List<Photo>
) : RecyclerView.Adapter<PhotoAdapter.PhotoViewHolder>(){

    inner class PhotoViewHolder(
        val binding: PhotoViewBinding
    ) : RecyclerView.ViewHolder(binding.root){

        fun databind(photo: Photo){
            binding.photo = photo
            binding.executePendingBindings()


            Glide.with(binding.imageview)
                .load(photo.thumbnailUrl)
                .into(binding.imageview)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = PhotoViewBinding.inflate(layoutInflater)
        return PhotoViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return photos.size
    }

    override fun onBindViewHolder(
        holder: PhotoViewHolder, position: Int)
            = holder.databind(photos[position])

    fun updatePhoto(photos: List<Photo>){
        this.photos = photos
        notifyDataSetChanged()
    }


}

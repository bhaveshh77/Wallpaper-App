package com.myapp.wallpaperapp.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.myapp.wallpaperapp.databinding.ItemWallpaperBinding
import com.myapp.wallpaperapp.domain.entities.WallpaperImage
import com.myapp.wallpaperapp.utils.BASE_URL

class WallpaperAdapter(private val wallpaperList : ArrayList<WallpaperImage>, private val onClick : (String) -> Unit) : RecyclerView.Adapter<WallpaperAdapter.WallpaperViewHolder>() {


    class WallpaperViewHolder(val binding : ItemWallpaperBinding): RecyclerView.ViewHolder(binding.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WallpaperViewHolder {
        return WallpaperViewHolder(ItemWallpaperBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount() = wallpaperList.size

    override fun onBindViewHolder(holder: WallpaperViewHolder, position: Int) {

        val wallpaper = wallpaperList[position]

        Glide.with(holder.itemView.context)
            .load(wallpaper.wallpaperUrl)
            .into(holder.binding.wallpaper)

        holder.binding.wallpaper.setOnClickListener {
            onClick(wallpaper.wallpaperUrl)
        }
    }
}
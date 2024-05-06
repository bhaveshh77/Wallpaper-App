package com.myapp.wallpaperapp.domain.repositories

import com.myapp.wallpaperapp.data.model.PicsumData
import com.myapp.wallpaperapp.domain.entities.WallpaperImage
import com.myapp.wallpaperapp.utils.Resource
import kotlinx.coroutines.flow.Flow

interface WallpaperRepository {

    fun getWallpaperImages() : Flow<Resource<List<WallpaperImage>>>
}
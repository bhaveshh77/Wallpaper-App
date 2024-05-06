package com.myapp.wallpaperapp.presentation.viewmodel

import com.myapp.wallpaperapp.domain.entities.WallpaperImage

sealed class WallpaperUIState {

    object Loading : WallpaperUIState()
    object EmptyList: WallpaperUIState()
    data class Success(val data : List<WallpaperImage>) : WallpaperUIState()
    data class Error(val message: String) : WallpaperUIState()
}
package com.myapp.wallpaperapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.myapp.wallpaperapp.domain.repositories.WallpaperRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import androidx.lifecycle.viewModelScope
import com.myapp.wallpaperapp.utils.Resource
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
// todo add the repository...
class WallpaperViewModel @Inject constructor(private val repository: WallpaperRepository): ViewModel() {

    private val _wallpaperList : MutableStateFlow<WallpaperUIState> =
        MutableStateFlow(WallpaperUIState.Loading)

    val wallpaperList get() = _wallpaperList.asStateFlow()
//    By returning _wallpaperList as a StateFlow using asStateFlow(), you are following a consistent pattern with the state flow architecture. This is important for code clarity and consistency, making it clear that you are dealing with a flow of state information.
//    In summary, the second variable wallpaperList acts as an interface for external parts of your application to observe the state of the wallpaper list. It provides a level of abstraction, encapsulation, and consistency in your code, contributing to better code organization and maintenance.

    fun fetchWallpapers() {

//        CoroutineExceptionHandler is used when the any exceptions are occurs in the coroutines
//        and we're doing all of this stuff below to avoid the error + debugging purposes!!

        val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
            throwable.printStackTrace()
            _wallpaperList.update { WallpaperUIState.Error(throwable.message.orEmpty()) }
        }

            viewModelScope.launch(Dispatchers.IO + exceptionHandler) { //  This is the exception handler defined earlier. It will be invoked if any exception occurs during the execution of the coroutine.
                repository.getWallpaperImages().collect { resource ->
                    when (resource) {

                        is Resource.Error -> {
                            _wallpaperList.update { WallpaperUIState.Error(resource.message.orEmpty()) }
                        }

                        is Resource.Success -> {
                            if (!resource.data.isNullOrEmpty()) {
                                _wallpaperList.update { WallpaperUIState.Success(resource.data) }
                            } else if(resource.data.isNullOrEmpty())  {
                                _wallpaperList.update { WallpaperUIState.EmptyList }
                            }
                            else {
                                _wallpaperList.update { WallpaperUIState.Error(resource.message.orEmpty()) }

                            }
                        }
                    }
                }
        }
    }
}
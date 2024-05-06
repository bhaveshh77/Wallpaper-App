package com.myapp.wallpaperapp.data.api

import android.telecom.Call
import com.myapp.wallpaperapp.data.model.PicsumData
import com.myapp.wallpaperapp.domain.entities.WallpaperImage

import retrofit2.http.GET
import retrofit2.http.Query

interface ImageApi {

        @GET("v2/list")
        suspend fun getWallpaperImages(@Query("page") page: Int = 1, @Query("limit") limit: Int = 100): List<PicsumData>

}
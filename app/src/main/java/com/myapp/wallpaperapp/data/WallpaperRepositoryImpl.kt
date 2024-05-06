package com.myapp.wallpaperapp.data
import android.util.Log
import com.myapp.wallpaperapp.data.api.ImageApi
import com.myapp.wallpaperapp.domain.entities.WallpaperImage
import com.myapp.wallpaperapp.domain.repositories.WallpaperRepository
import com.myapp.wallpaperapp.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class WallpaperRepositoryImpl @Inject constructor(private val imageApi: ImageApi) : WallpaperRepository {

    override fun getWallpaperImages(): Flow<Resource<List<WallpaperImage>>> = flow {

        val response = imageApi.getWallpaperImages(page = 1)
        try {
            response?.let { it ->// let is a keyword in kotlin which lets you execute a code block within a object
                val movies: List<WallpaperImage> = it.map {
                    WallpaperImage(it.downloadUrl.orEmpty())
                }
                emit(Resource.Success(movies))
            }

        } catch (e: HttpException) {
            emit(Resource.Error(null, e.message()))
            Log.e("Error", e.message())
        } catch (e: IOException) {
            emit(Resource.Error(null, e.message ?: "Network connection Error"))
        }
    }
}
package com.myapp.wallpaperapp.presentation.view

import android.app.WallpaperManager
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.myapp.wallpaperapp.databinding.ActivityMainBinding
import com.myapp.wallpaperapp.domain.entities.WallpaperImage
import com.myapp.wallpaperapp.presentation.adapter.WallpaperAdapter
import com.myapp.wallpaperapp.presentation.ui.GridSpace
import com.myapp.wallpaperapp.presentation.viewmodel.WallpaperUIState
import com.myapp.wallpaperapp.presentation.viewmodel.WallpaperViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.URL


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    private val wallpaperViewModel: WallpaperViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setUpUIState()

        wallpaperViewModel.fetchWallpapers()
    }

    private fun setUpUIState() {

        lifecycleScope.launch {

            wallpaperViewModel.wallpaperList.collect {wallpaperState ->

                when(wallpaperState) {

                    is WallpaperUIState.Success -> {
                        binding.progressBar.visibility = View.GONE
                        setUpRecyclerView(wallpaperState.data)
                    }

                    is WallpaperUIState.Loading -> {
                        binding.progressBar.visibility = View.VISIBLE
                        Toast.makeText(this@MainActivity, "Wallpapers are currently loading!!", Toast.LENGTH_SHORT).show()
                    }

                    is WallpaperUIState.Error -> {
                        binding.progressBar.visibility = View.VISIBLE
                        Toast.makeText(this@MainActivity, "Some Error Occurred!!", Toast.LENGTH_SHORT).show()
                        Log.e("Error", wallpaperState.message)
                    }

                    is WallpaperUIState.EmptyList -> {
                        binding.progressBar.visibility = View.VISIBLE
                        Toast.makeText(this@MainActivity, "Wallpapers are currently Empty!!!!", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    private fun setUpRecyclerView(list:List<WallpaperImage>) {
//        val list = ArrayList<WallpaperImage>()

        val gridLayoutManager = GridLayoutManager(this, 2)

//        binding.recyclerViewMovies.setLayoutManager(gridLayoutManager)


        val wallpaperList = ArrayList(list)
        val wallpaperAdapter = WallpaperAdapter(wallpaperList, this::onWallpaperClick)
        binding.wallpaperList.apply {
            layoutManager = gridLayoutManager
            addItemDecoration(GridSpace(2, 20, true))
            adapter = wallpaperAdapter
        }
    }

    private fun onWallpaperClick(wallpaperLink : String) {

        AlertDialog.Builder(this@MainActivity)
            .setTitle("Do you want to set this image as a Wallpaper?")
            .setCancelable(false)
            .setPositiveButton("Yes") { _, _ ->
                lifecycleScope.launch(Dispatchers.IO) {
                    try {
                        val inputStream = URL(wallpaperLink).openStream()
                        WallpaperManager.getInstance(this@MainActivity).setStream(inputStream)

                        withContext(Dispatchers.Main) {
                            Toast.makeText(this@MainActivity, "Wallpaper Updated!!", Toast.LENGTH_SHORT).show()
                        }
                    } catch (e: Exception) {
                        // Handle exception, log it, or display an error message
                        e.printStackTrace()
                    }
                }
            }.setNegativeButton("No") { dialog, _ ->
                dialog.dismiss()
            }.show()
    }


}
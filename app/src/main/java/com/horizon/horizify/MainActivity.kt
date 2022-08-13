package com.horizon.horizify

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.google.android.material.navigation.NavigationBarView
import com.horizon.horizify.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), NavigationBarView.OnItemSelectedListener {

    private lateinit var binding: ActivityMainBinding
    private var isInit = false

    private val navController: NavController by lazy {
        findNavController(R.id.fragment_container_view)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        // Hide the status bar.
//        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
    }

    override fun onStart() {
        super.onStart()
        if (!isInit) {
            isInit = true
            initializeNavigation()
        }
    }

    private fun initializeNavigation() {
        val nav = navController.navInflater.inflate(R.navigation.nav_main)
        navController.graph = nav.apply {
            binding.layoutToolbar.isVisible = false
            setStartDestination(R.id.homeFragment)
        }
        binding.bottomNavigation.setOnItemSelectedListener(this@MainActivity)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.bottom_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        Log.e("menu : ", item.itemId.toString())
        binding.layoutToolbar.isVisible = true
        when (item.itemId) {
            R.id.menu_home -> {
                binding.layoutToolbar.isVisible = false
                navController.navigate(R.id.action_global_homeFragment)
            }
            R.id.menu_videos -> navController.navigate(R.id.action_global_videoFragment)
            R.id.menu_podcast -> navController.navigate(R.id.action_global_podcastFragment)
            R.id.menu_calendar -> navController.navigate(R.id.action_global_calendarFragment)
            R.id.menu_settings -> navController.navigate(R.id.action_global_homeFragment)
        }
        return true
    }
}
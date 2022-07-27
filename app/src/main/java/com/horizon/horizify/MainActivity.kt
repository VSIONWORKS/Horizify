package com.horizon.horizify

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.horizon.horizify.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val navController: NavController by lazy {
        findNavController(R.id.fragment_container_view)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onStart() {
        super.onStart()
        initializeNavigation()
    }

    private fun initializeNavigation() {
        val nav = navController.navInflater.inflate(R.navigation.nav_main)
        navController.graph = nav.apply { setStartDestination(R.id.homeFragment) }
    }
}
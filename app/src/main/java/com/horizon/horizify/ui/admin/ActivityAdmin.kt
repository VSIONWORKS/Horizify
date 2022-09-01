package com.horizon.horizify.ui.admin

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.horizon.horizify.databinding.ActivityAdminBinding
import com.horizon.horizify.ui.admin.viewmodel.AdminViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class ActivityAdmin : AppCompatActivity() {

    private lateinit var binding: ActivityAdminBinding

    private val adminViewModel : AdminViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdminBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loadBannerCarousel()

        binding.back.setOnClickListener { finish() }
    }

    private fun loadBannerCarousel() {
//        val cards = adminViewModel.getBannerCarousels()
//        with(binding) {
//            vpCarousel.adapter = HomeViewPagerAdapter(cards)
//            vpCarousel.setPageTransformer(DepthPageTransformer())
//            headerDots.attachTo(vpCarousel)
//        }
    }
}
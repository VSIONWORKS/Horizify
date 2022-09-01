package com.horizon.horizify.ui.home.item

import android.util.Log
import android.view.View
import com.horizon.horizify.R
import com.horizon.horizify.databinding.HomeHeaderItemBinding
import com.horizon.horizify.ui.home.adapter.HomeViewPagerAdapter
import com.horizon.horizify.ui.home.viewmodel.HomeViewModel
import com.horizon.horizify.utils.DepthPageTransformer
import com.horizon.horizify.utils.ItemActionWithValue
import com.horizon.horizify.utils.SingletonHandler
import com.horizon.horizify.utils.SingletonHandler.runnable
import com.xwray.groupie.viewbinding.BindableItem
import java.util.*

class HomeHeaderItem(viewModel: HomeViewModel) : BindableItem<HomeHeaderItemBinding>() {

    private val handler = SingletonHandler.getCarouselHandler()
    private val timer = SingletonHandler.getCarouselTimer()
    private val cards = viewModel.getHeaderCardImages()

    override fun bind(viewBinding: HomeHeaderItemBinding, position: Int) {
        with(viewBinding) {

            var currentItem = headerViewPager.currentItem

            headerViewPager.adapter = HomeViewPagerAdapter(cards = cards, onClick = ItemActionWithValue { Log.e("todo here", it.toString()) })
            headerViewPager.setPageTransformer(DepthPageTransformer())
            headerDots.attachTo(headerViewPager)

            runnable = Runnable {
                currentItem = if (headerViewPager.currentItem == cards.size-1) {
                    0
                } else headerViewPager.currentItem + 1
                //The second parameter ensures smooth scrolling
                headerViewPager.setCurrentItem(currentItem, true)
            }

            timer?.schedule(object : TimerTask() {
                // task to be scheduled
                override fun run() {
                    handler.post(runnable!!)
                }
            }, 3500, 3500)
        }
    }

    override fun getLayout(): Int = R.layout.home_header_item

    override fun initializeViewBinding(view: View): HomeHeaderItemBinding = HomeHeaderItemBinding.bind(view)
}
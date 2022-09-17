package com.horizon.horizify.ui.home.item

import android.view.View
import com.horizon.horizify.R
import com.horizon.horizify.common.model.BannerModel
import com.horizon.horizify.common.model.MainBannerModel
import com.horizon.horizify.databinding.HomeHeaderItemBinding
import com.horizon.horizify.ui.home.adapter.HomeViewPagerAdapter
import com.horizon.horizify.ui.home.model.HeaderCardModel
import com.horizon.horizify.ui.home.viewmodel.HomeViewModel
import com.horizon.horizify.utils.*
import com.horizon.horizify.utils.SingletonHandler.runnable
import com.xwray.groupie.viewbinding.BindableItem
import java.util.*

class HomeHeaderItem(viewModel: HomeViewModel) : BindableItem<HomeHeaderItemBinding>() {

    private val cards = viewModel.getHeaderCardImages()
    var onClick: ItemActionWithValue<BannerModel>? = null
    var model by BindableItemObserver(MainBannerModel())

    override fun bind(viewBinding: HomeHeaderItemBinding, position: Int) {
        val handler = SingletonHandler.getCarouselHandler()
        val timer = SingletonHandler.getCarouselTimer()

        with(viewBinding) {

            var currentItem = headerViewPager.currentItem

            val bannerCarousel: ArrayList<HeaderCardModel> = arrayListOf()
            bannerCarousel.add(HeaderCardModel(banner = model.primary))
            model.banners.forEach {
                bannerCarousel.add(HeaderCardModel(banner = it))
            }

            headerViewPager.apply {
                adapter = HomeViewPagerAdapter(
                    cards = bannerCarousel,
                    onClick = ItemAction { onClick?.actionCallback?.invoke(bannerCarousel[headerViewPager.currentItem].banner) }
                )
                setPageTransformer(DepthPageTransformer())
            }
            headerDots.attachTo(headerViewPager)

            if (bannerCarousel.size > 1) {
                runnable = Runnable {
                    currentItem = if (headerViewPager.currentItem == bannerCarousel.size - 1) {
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
    }

    override fun getLayout(): Int = R.layout.home_header_item

    override fun initializeViewBinding(view: View): HomeHeaderItemBinding = HomeHeaderItemBinding.bind(view)
}
package com.horizon.horizify.ui.admin.item.main

import android.view.View
import androidx.core.view.isVisible
import com.horizon.horizify.R
import com.horizon.horizify.commonModel.BannerModel
import com.horizon.horizify.commonModel.CarouselModel
import com.horizon.horizify.databinding.AdminManageCarouselItemBinding
import com.horizon.horizify.extensions.loadFitCenter
import com.horizon.horizify.ui.admin.AdminSetupEnum
import com.horizon.horizify.ui.admin.viewmodel.AdminViewModel
import com.horizon.horizify.ui.home.adapter.HomeViewPagerAdapter
import com.horizon.horizify.utils.BindableItemObserver
import com.horizon.horizify.utils.DepthPageTransformer
import com.horizon.horizify.utils.ItemActionWithValue
import com.xwray.groupie.ExpandableGroup
import com.xwray.groupie.ExpandableItem
import com.xwray.groupie.viewbinding.BindableItem

class AdminManageCarouselItem(val viewModel: AdminViewModel, val onClick: ItemActionWithValue<AdminSetupEnum>) : BindableItem<AdminManageCarouselItemBinding>(), ExpandableItem {

    private lateinit var expandableGroup: ExpandableGroup

    var bannerModel by BindableItemObserver(BannerModel())
    var carouselModel by BindableItemObserver(CarouselModel())

    override fun bind(viewBinding: AdminManageCarouselItemBinding, position: Int) {
        with(viewBinding) {

            expandableGroup = ExpandableGroup(this@AdminManageCarouselItem, false)

            loadBannerCarousel()
            updateLayout()

            loadPrimaryBanner()
            loadCarousel()

            cardManageBannerCarousel.setOnClickListener { onExpandItem() }
            imgDropdown.setOnClickListener { onExpandItem() }

            cardPrimaryBanner.setOnClickListener { onClick.actionCallback(AdminSetupEnum.PRIMARY_BANNER) }
        }
    }

    override fun getLayout(): Int = R.layout.admin_manage_carousel_item

    override fun initializeViewBinding(view: View): AdminManageCarouselItemBinding = AdminManageCarouselItemBinding.bind(view)

    override fun setExpandableGroup(onToggleListener: ExpandableGroup) {
        expandableGroup = onToggleListener
    }

    private fun AdminManageCarouselItemBinding.loadBannerCarousel() {
        val cards = viewModel.getBannerCarousels()
        vpCarousel.adapter = HomeViewPagerAdapter(cards = cards, onClick = ItemActionWithValue { onClick.actionCallback(AdminSetupEnum.BANNER) })
        vpCarousel.setPageTransformer(DepthPageTransformer())
        headerDots.attachTo(vpCarousel)
    }

    private fun AdminManageCarouselItemBinding.onExpandItem() {
        expandableGroup.onToggleExpanded()
        updateLayout()
    }

    private fun AdminManageCarouselItemBinding.updateLayout() {
        if (expandableGroup.isExpanded) {
            layoutBannerCarousel.isVisible = true
            imgDropdown.rotation = 180f
        }
        else {
            layoutBannerCarousel.isVisible = false
            imgDropdown.rotation = 0f
        }
    }

    private fun AdminManageCarouselItemBinding.loadPrimaryBanner() {
        if (bannerModel.image.isNotEmpty()) {
            imageBanner.loadFitCenter(bannerModel.image)
            imgAdd.isVisible = false
        }
    }

    private fun AdminManageCarouselItemBinding.loadCarousel() {

    }
}
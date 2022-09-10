package com.horizon.horizify.ui.admin.item.main

import android.view.View
import androidx.core.view.isVisible
import com.horizon.horizify.R
import com.horizon.horizify.commonModel.BannerTypeModel
import com.horizon.horizify.commonModel.MainBannerModel
import com.horizon.horizify.databinding.AdminManageCarouselItemBinding
import com.horizon.horizify.extensions.loadFitCenter
import com.horizon.horizify.ui.admin.AdminSetupEnum
import com.horizon.horizify.ui.admin.viewmodel.AdminViewModel
import com.horizon.horizify.ui.home.adapter.HomeViewPagerAdapter
import com.horizon.horizify.ui.home.model.HeaderCardModel
import com.horizon.horizify.utils.BindableItemObserver
import com.horizon.horizify.utils.DepthPageTransformer
import com.horizon.horizify.utils.ItemAction
import com.horizon.horizify.utils.ItemActionWithValue
import com.xwray.groupie.ExpandableGroup
import com.xwray.groupie.ExpandableItem
import com.xwray.groupie.viewbinding.BindableItem

class AdminManageCarouselItem(val viewModel: AdminViewModel, val onClick: ItemActionWithValue<BannerTypeModel>) : BindableItem<AdminManageCarouselItemBinding>(), ExpandableItem {

    private lateinit var expandableGroup: ExpandableGroup

    var model by BindableItemObserver(MainBannerModel())

    override fun bind(viewBinding: AdminManageCarouselItemBinding, position: Int) {
        with(viewBinding) {

            expandableGroup = ExpandableGroup(this@AdminManageCarouselItem, false)

            loadPrimaryBanner()
            loadCarousel()
            updateLayout()

            cardManageBannerCarousel.setOnClickListener { onExpandItem() }
            imgDropdown.setOnClickListener { onExpandItem() }

            cardPrimaryBanner.setOnClickListener { onClick.actionCallback(BannerTypeModel(AdminSetupEnum.PRIMARY_BANNER, model.primary)) }
        }
    }

    override fun getLayout(): Int = R.layout.admin_manage_carousel_item

    override fun initializeViewBinding(view: View): AdminManageCarouselItemBinding = AdminManageCarouselItemBinding.bind(view)

    override fun setExpandableGroup(onToggleListener: ExpandableGroup) {
        expandableGroup = onToggleListener
    }

    private fun AdminManageCarouselItemBinding.loadPrimaryBanner() {
        if (model.primary.image.isNotEmpty()) {
            imageBanner.loadFitCenter(model.primary.image)
            imgAdd.isVisible = false
        }
    }

    private fun AdminManageCarouselItemBinding.loadCarousel() {
        val cardList = arrayListOf<HeaderCardModel>()
        model.banners.forEach {
            cardList.add(HeaderCardModel(banner = it))
        }
        cardList.add(HeaderCardModel(isDefault = true))

        vpCarousel.apply {
            adapter = HomeViewPagerAdapter(
                cards = cardList,
                onClick = ItemAction {
                    val banner = cardList[currentItem].banner
                    onClick.actionCallback(BannerTypeModel(AdminSetupEnum.BANNER, banner))
                }
            )
            setPageTransformer(DepthPageTransformer())
            headerDots.attachTo(this)
        }
    }

    private fun AdminManageCarouselItemBinding.onExpandItem() {
        expandableGroup.onToggleExpanded()
        updateLayout()
    }

    private fun AdminManageCarouselItemBinding.updateLayout() {
        if (expandableGroup.isExpanded) {
            layoutBannerCarousel.isVisible = true
            imgDropdown.rotation = 180f
        } else {
            layoutBannerCarousel.isVisible = false
            imgDropdown.rotation = 0f
        }
    }
}
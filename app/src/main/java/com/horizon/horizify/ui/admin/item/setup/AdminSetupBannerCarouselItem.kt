package com.horizon.horizify.ui.admin.item.setup

import android.view.View
import android.widget.Toast
import com.horizon.horizify.R
import com.horizon.horizify.commonModel.BannerModel
import com.horizon.horizify.commonModel.ImageUriModel
import com.horizon.horizify.databinding.AdminSetupBannerCarouselItemBinding
import com.horizon.horizify.ui.admin.AdminSetupEnum
import com.horizon.horizify.ui.admin.viewmodel.AdminViewModel
import com.horizon.horizify.utils.BindableItemObserver
import com.horizon.horizify.utils.Constants.ADD_BANNER
import com.horizon.horizify.utils.Constants.PRIMARY_BANNER
import com.horizon.horizify.utils.ItemAction
import com.xwray.groupie.viewbinding.BindableItem

class AdminSetupBannerCarouselItem(val viewModel: AdminViewModel, private val isPrimary: Boolean, val onImagePick: ItemAction) : BindableItem<AdminSetupBannerCarouselItemBinding>() {

    val model by BindableItemObserver(BannerModel())
    var imageUriModel by BindableItemObserver(ImageUriModel())

    override fun bind(viewBinding: AdminSetupBannerCarouselItemBinding, position: Int) {
        with(viewBinding) {
            textBanner.text = if (isPrimary) PRIMARY_BANNER else ADD_BANNER
            cardPrimaryBanner.setOnClickListener { onImagePick.actionCallback.invoke() }

            val uri = imageUriModel
            imageBanner.setImageURI(uri.uri)

            btnSave.setOnClickListener { validate() }
        }
    }

    override fun getLayout(): Int = R.layout.admin_setup_banner_carousel_item

    override fun initializeViewBinding(view: View): AdminSetupBannerCarouselItemBinding = AdminSetupBannerCarouselItemBinding.bind(view)

    private fun AdminSetupBannerCarouselItemBinding.validate() {
        val title = textTitle.text.toString()
        val description = textDescription.text.toString()
        val linkCaption = textLinkCaption.text.toString()
        val link = textLink.text.toString()

        if (title.isEmpty() || description.isEmpty()) Toast.makeText(root.context, "Please input required fields", Toast.LENGTH_SHORT).show()
        else if (linkCaption.isNotEmpty() and link.isEmpty()) Toast.makeText(root.context, "Please input link", Toast.LENGTH_SHORT).show()
        else if (linkCaption.isEmpty() and link.isNotEmpty()) Toast.makeText(root.context, "Please input link caption", Toast.LENGTH_SHORT).show()
        else save(
            BannerModel(
                title = title,
                description = description,
                linkCaption = linkCaption,
                link = link
            )
        )
    }

    private fun save(bannerModel: BannerModel) {
        val type = if (isPrimary) AdminSetupEnum.PRIMARY_BANNER else AdminSetupEnum.BANNER
        viewModel.saveBanner(type, bannerModel)
    }
}
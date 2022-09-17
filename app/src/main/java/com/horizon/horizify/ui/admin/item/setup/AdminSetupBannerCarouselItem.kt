package com.horizon.horizify.ui.admin.item.setup

import android.inputmethodservice.InputMethodService
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.view.isVisible
import com.horizon.horizify.R
import com.horizon.horizify.common.model.BannerTypeModel
import com.horizon.horizify.common.model.ImageUriModel
import com.horizon.horizify.databinding.AdminSetupBannerCarouselItemBinding
import com.horizon.horizify.extensions.loadFitCenter
import com.horizon.horizify.extensions.trimAllSpaces
import com.horizon.horizify.ui.admin.AdminSetupEnum
import com.horizon.horizify.ui.admin.viewmodel.AdminViewModel
import com.horizon.horizify.utils.BindableItemObserver
import com.horizon.horizify.utils.Constants.ADD_BANNER
import com.horizon.horizify.utils.Constants.PRIMARY_BANNER
import com.horizon.horizify.utils.ItemAction
import com.xwray.groupie.viewbinding.BindableItem

class AdminSetupBannerCarouselItem(val viewModel: AdminViewModel, val onImagePick: ItemAction) : BindableItem<AdminSetupBannerCarouselItemBinding>() {

    private var postId: String = ""
    private var isUpdate: Boolean = false
    var model by BindableItemObserver(BannerTypeModel())
    var imageUriModel by BindableItemObserver(ImageUriModel())

    override fun bind(viewBinding: AdminSetupBannerCarouselItemBinding, position: Int) {
        with(viewBinding) {
            postId = model.banner.title.trimAllSpaces()
            when (model.type) {
                AdminSetupEnum.PRIMARY_BANNER -> textBanner.text = PRIMARY_BANNER
                AdminSetupEnum.BANNER -> {
                    textBanner.text = ADD_BANNER
                    if (!model.banner.image.isNullOrEmpty()) btnRemove.isVisible = true
                }
            }
            cardPrimaryBanner.setOnClickListener { onImagePick.actionCallback.invoke() }

            setUpBannerValues()

            btnSave.setOnClickListener { validate() }
            btnRemove.setOnClickListener { removeBanner() }
        }
    }

    override fun getLayout(): Int = R.layout.admin_setup_banner_carousel_item

    override fun initializeViewBinding(view: View): AdminSetupBannerCarouselItemBinding = AdminSetupBannerCarouselItemBinding.bind(view)

    private fun AdminSetupBannerCarouselItemBinding.setUpBannerValues() {

        if (imageUriModel.uri != null) {
            // used when selecting image from device
            imageBanner.setImageURI(imageUriModel.uri)
            imgAdd.isVisible = false
            if (!model.banner.image.isNullOrEmpty()) isUpdate = true

        } else {
            // used to load banner image from fetched data
            imageBanner.loadFitCenter(model.banner.image)
            if (!model.banner.image.isNullOrEmpty()) {
                btnSave.text = "Update Banner"
                imgAdd.isVisible = false
            }
        }

        textTitle.setText(model.banner.title)
        textDescription.setText(model.banner.description)
        textLinkCaption.setText(model.banner.linkCaption)
        textLink.setText(model.banner.link)
        checkboxXb.isChecked = model.banner.isExternalBrowser
    }

    private fun AdminSetupBannerCarouselItemBinding.validate() {

        val imm: InputMethodManager = root.context.getSystemService(InputMethodService.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(root.windowToken, 0)

        val title = textTitle.text.toString()
        val description = textDescription.text.toString()
        val linkCaption = textLinkCaption.text.toString()
        val link = textLink.text.toString()

        if (title.isEmpty() || description.isEmpty()) Toast.makeText(root.context, "Please input required fields", Toast.LENGTH_SHORT).show()
        else if (linkCaption.isNotEmpty() and link.isEmpty()) Toast.makeText(root.context, "Please input link", Toast.LENGTH_SHORT).show()
        else if (linkCaption.isEmpty() and link.isNotEmpty()) Toast.makeText(root.context, "Please input link caption", Toast.LENGTH_SHORT).show()
        else if (model.banner.image.isNullOrEmpty() && imageUriModel.uri == null) Toast.makeText(root.context, "Upload na Image", Toast.LENGTH_SHORT).show()
        else {
            var banner = model.banner.copy(
                title = title,
                description = description,
                linkCaption = linkCaption,
                link = link,
                isExternalBrowser = checkboxXb.isChecked
            )

            with(viewModel) {
                if (!model.banner.image.isNullOrEmpty() && (isUpdate || model.banner != banner)) {
                    updateBanner(model.type, banner, postId)
                }
                else {
                    banner = banner.copy(date = viewModel.getCurrentDateTime())
                    save(model.type, banner)
                }
            }
        }
    }

    private fun removeBanner() {
        viewModel.removeBanner(model.type, model.banner, postId)
    }
}
package com.horizon.horizify.ui.admin

import android.app.Activity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import com.github.dhaval2404.imagepicker.ImagePicker
import com.horizon.horizify.base.GroupieFragment
import com.horizon.horizify.extensions.collectOnStart
import com.horizon.horizify.extensions.handleBackPress
import com.horizon.horizify.extensions.setBody
import com.horizon.horizify.ui.admin.item.AdminHeaderItem
import com.horizon.horizify.ui.admin.item.setup.AdminSetupBannerCarouselItem
import com.horizon.horizify.ui.admin.item.setup.AdminSetupNetworkItem
import com.horizon.horizify.ui.admin.viewmodel.AdminViewModel
import com.horizon.horizify.utils.Constants.SETUP_BANNER
import com.horizon.horizify.utils.ItemAction
import com.horizon.horizify.utils.PageState
import com.xwray.groupie.Section
import org.koin.androidx.viewmodel.ext.android.viewModel

class AdminSetupFragment : GroupieFragment() {

    private var setUpSection: Section = Section()
    private lateinit var adminSetupBannerCarouselItem: AdminSetupBannerCarouselItem
    private lateinit var adminSetupNetworkItem: AdminSetupNetworkItem

    private val adminViewModel: AdminViewModel by viewModel()

    override fun onViewSetup(view: View, savedInstanceState: Bundle?) {
        val bannerTypeModel = adminViewModel.setupBanner()

        val header = AdminHeaderItem(title = SETUP_BANNER, onBack = ItemAction { onBackPressed() })

        when (bannerTypeModel.type) {
            AdminSetupEnum.PRIMARY_BANNER, AdminSetupEnum.BANNER -> {
                adminSetupBannerCarouselItem = AdminSetupBannerCarouselItem(adminViewModel, onImagePick = ItemAction { openImagePicker() })
                setUpSection.add(adminSetupBannerCarouselItem)
            }
            AdminSetupEnum.NETWORK -> {
                adminSetupNetworkItem = AdminSetupNetworkItem()
                setUpSection.add(adminSetupNetworkItem)
            }
        }

        with(root) {
            setHeader(header)
            setBody(setUpSection)
        }

        startCollect()

        handleBackPress { onBackPressed() }

    }

    private fun startCollect() {
        with(adminViewModel) {
            bannerTypeModel.collectOnStart(viewLifecycleOwner, adminSetupBannerCarouselItem::model)
            imageUriModel.collectOnStart(viewLifecycleOwner, adminSetupBannerCarouselItem::imageUriModel)
            pageState.collectOnStart(viewLifecycleOwner, ::handlePageState)
        }
    }

    private val startForProfileImageResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
        val resultCode = result.resultCode
        val data = result.data

        when (resultCode) {
            Activity.RESULT_OK -> {
                //Image Uri will not be null for RESULT_OK
                val fileUri = data?.data!!
                adminViewModel.setNewImageUri(fileUri)
            }
            ImagePicker.RESULT_ERROR -> {
                Toast.makeText(context, ImagePicker.getError(data), Toast.LENGTH_SHORT).show()
            }
            else -> {
                Toast.makeText(context, "Task Cancelled", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun openImagePicker() {
        ImagePicker.with(requireActivity())
            .createIntent { startForProfileImageResult.launch(it) }
    }

    private fun onBackPressed() = popBack()

    private fun handlePageState(state: PageState) {
        binding.apply {
            when (state) {
                PageState.Loading -> showLoader()
                PageState.Completed -> {
                    hideLoader()
                    if (adminViewModel.saveState.value == state) onBackPressed()
                }
                PageState.Error -> hideLoader()
            }
        }
    }
}
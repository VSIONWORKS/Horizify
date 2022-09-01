package com.horizon.horizify.ui.admin

import android.os.Bundle
import android.view.View
import com.horizon.horizify.base.GroupieFragment
import com.horizon.horizify.extensions.collectOnStart
import com.horizon.horizify.extensions.handleBackPress
import com.horizon.horizify.extensions.launchOnStart
import com.horizon.horizify.extensions.setBody
import com.horizon.horizify.ui.admin.item.AdminHeaderItem
import com.horizon.horizify.ui.admin.item.main.AdminManageCarouselItem
import com.horizon.horizify.ui.admin.item.main.AdminManageNetworkItem
import com.horizon.horizify.ui.admin.viewmodel.AdminViewModel
import com.horizon.horizify.utils.Constants.ADMIN
import com.horizon.horizify.utils.ItemAction
import com.horizon.horizify.utils.ItemActionWithValue
import com.horizon.horizify.utils.PageId
import com.horizon.horizify.utils.PageState
import com.xwray.groupie.ExpandableGroup
import org.koin.androidx.viewmodel.ext.android.viewModel

class AdminFragment : GroupieFragment() {

    private lateinit var manageCarouseItem: AdminManageCarouselItem
    private val adminViewModel: AdminViewModel by viewModel()

    override fun onViewSetup(view: View, savedInstanceState: Bundle?) {

        val header = AdminHeaderItem(title = ADMIN, onBack = ItemAction { onBackPressed() })
        manageCarouseItem = AdminManageCarouselItem(
            adminViewModel,
            ItemActionWithValue {
                when (it) {
                    AdminSetupEnum.PRIMARY_BANNER -> navigateWithParams(PageId.ADMIN_SETUP_PAGE, AdminModule.SETUP_MODE to AdminModule.SETUP_PRIMMARY_BANNER)
                    AdminSetupEnum.BANNER -> navigateWithParams(PageId.ADMIN_SETUP_PAGE, AdminModule.SETUP_MODE to AdminModule.SETUP_BANNER)
                }
            }
        )
        val manageNetworkItem = ExpandableGroup(AdminManageNetworkItem(adminViewModel), false)

        with(root) {
            setHeader(header)
            setBody(manageCarouseItem, manageNetworkItem)
        }

        with(adminViewModel) {
            primaryBanner.collectOnStart(viewLifecycleOwner, manageCarouseItem::bannerModel)
            carousel.collectOnStart(viewLifecycleOwner, manageCarouseItem::carouselModel)
            pageState.collectOnStart(viewLifecycleOwner, ::handlePageState)
            launchOnStart { loadBanners() }
        }

        handleBackPress { onBackPressed() }
    }


    private fun onBackPressed() = popBack()

    private fun handlePageState(state: PageState) {
        binding.apply {
            when (state) {
                PageState.Loading -> showLoader()
                PageState.Completed -> hideLoader()
                PageState.Error -> hideLoader()
            }
        }
    }
}
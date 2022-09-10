package com.horizon.horizify.ui.admin

import android.os.Bundle
import android.view.View
import com.horizon.horizify.base.GroupieFragment
import com.horizon.horizify.extensions.collectOnStart
import com.horizon.horizify.extensions.handleBackPress
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
        // initialize fetching of banner carousel
        adminViewModel.getBannerCarousel()

        val header = AdminHeaderItem(title = ADMIN, onBack = ItemAction { onBackPressed() })
        manageCarouseItem = AdminManageCarouselItem(
            adminViewModel,
            ItemActionWithValue {
                adminViewModel.saveBanner(it)
                navigateToPage(PageId.ADMIN_SETUP_PAGE)
            }
        )
        val manageNetworkItem = ExpandableGroup(AdminManageNetworkItem(adminViewModel), false)

        with(root) {
            setHeader(header)
            setBody(manageCarouseItem, manageNetworkItem)
        }

        with(adminViewModel) {
            bannerCarousel.collectOnStart(viewLifecycleOwner, manageCarouseItem::model)
            pageState.collectOnStart(viewLifecycleOwner, ::handlePageState)
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
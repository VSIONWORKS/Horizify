package com.horizon.horizify.ui.menu

import android.os.Bundle
import android.view.View
import com.horizon.horizify.base.GroupieFragment
import com.horizon.horizify.extensions.setBody
import com.horizon.horizify.ui.menu.item.MenuBodyItemFragment
import com.horizon.horizify.ui.menu.viewmodel.MenuViewModel
import com.horizon.horizify.utils.ItemActionWithValue
import com.horizon.horizify.utils.PageId
import org.koin.androidx.viewmodel.ext.android.viewModel

class MenuBodyFragment : GroupieFragment() {

    private val menuViewModel: MenuViewModel by viewModel()

    override fun onViewSetup(view: View, savedInstanceState: Bundle?) {
        val bodyItem = MenuBodyItemFragment(
            onMenuClick = ItemActionWithValue { menuClick(it) }
        )

        with(root) {
            setBody(bodyItem)
        }
    }

    private fun menuClick(menu: MenuEnum) {
        when (menu) {
            MenuEnum.ADMIN -> {
                navigateToPage(PageId.ADMIN)
            }
            MenuEnum.PROFILE -> {
                navigateToPage(PageId.COMING_SOON)
            }
            MenuEnum.CALENDAR -> {
                navigateToPage(PageId.CALENDAR)
            }
            MenuEnum.PREFERENCES -> {
                navigateToPage(PageId.COMING_SOON)
            }
            MenuEnum.SHARE -> {
                navigateToPage(PageId.COMING_SOON)
            }
            MenuEnum.POLICY -> {
                navigateToPage(PageId.COMING_SOON)
            }
            MenuEnum.TERMS -> {
                navigateToPage(PageId.COMING_SOON)
            }
            MenuEnum.ABOUT -> {
                navigateToPage(PageId.COMING_SOON)
            }
        }
    }
}
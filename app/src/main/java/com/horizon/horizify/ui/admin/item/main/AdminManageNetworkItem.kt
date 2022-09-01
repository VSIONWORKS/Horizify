package com.horizon.horizify.ui.admin.item.main

import android.view.View
import androidx.core.view.isVisible
import com.horizon.horizify.R
import com.horizon.horizify.databinding.AdminManageNetworkItemBinding
import com.horizon.horizify.ui.admin.viewmodel.AdminViewModel
import com.xwray.groupie.ExpandableGroup
import com.xwray.groupie.ExpandableItem
import com.xwray.groupie.viewbinding.BindableItem

class AdminManageNetworkItem(val viewModel: AdminViewModel)  :BindableItem<AdminManageNetworkItemBinding>(), ExpandableItem {

    private lateinit var expandableGroup: ExpandableGroup

    override fun bind(viewBinding: AdminManageNetworkItemBinding, position: Int) {
        with(viewBinding){
            updateLayout()
            cardManageNetwork.setOnClickListener { onExpandItem() }
            imgDropdown.setOnClickListener { onExpandItem() }
        }
    }

    override fun getLayout(): Int = R.layout.admin_manage_network_item

    override fun initializeViewBinding(view: View): AdminManageNetworkItemBinding = AdminManageNetworkItemBinding.bind(view)

    override fun setExpandableGroup(onToggleListener: ExpandableGroup) {
        expandableGroup = onToggleListener
    }

    private fun AdminManageNetworkItemBinding.onExpandItem() {
        updateLayout()
    }

    private fun AdminManageNetworkItemBinding.updateLayout() {
        if (expandableGroup.isExpanded) {
            layoutNetwork.isVisible = true
            imgDropdown.rotation = 180f
        }
        else {
            layoutNetwork.isVisible = false
            imgDropdown.rotation = 00f
        }
    }
}
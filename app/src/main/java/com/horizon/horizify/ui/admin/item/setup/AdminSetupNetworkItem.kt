package com.horizon.horizify.ui.admin.item.setup

import android.view.View
import com.horizon.horizify.R
import com.horizon.horizify.databinding.AdminSetupNetworkItemBinding
import com.xwray.groupie.viewbinding.BindableItem

class AdminSetupNetworkItem : BindableItem<AdminSetupNetworkItemBinding>() {

    override fun bind(viewBinding: AdminSetupNetworkItemBinding, position: Int) {
        with(viewBinding){

        }
    }

    override fun getLayout(): Int = R.layout.admin_setup_network_item

    override fun initializeViewBinding(view: View): AdminSetupNetworkItemBinding = AdminSetupNetworkItemBinding.bind(view)
}
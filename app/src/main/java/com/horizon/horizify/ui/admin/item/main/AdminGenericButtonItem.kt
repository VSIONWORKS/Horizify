package com.horizon.horizify.ui.admin.item.main

import android.view.View
import com.horizon.horizify.R
import com.horizon.horizify.databinding.AdminManageGenericButtonItemBinding
import com.horizon.horizify.ui.admin.AdminManageEnum
import com.horizon.horizify.utils.ItemActionWithValue
import com.xwray.groupie.viewbinding.BindableItem

class AdminGenericButtonItem(val type: AdminManageEnum, val title: String, val onClick: ItemActionWithValue<AdminManageEnum>) : BindableItem<AdminManageGenericButtonItemBinding>() {
    override fun bind(viewBinding: AdminManageGenericButtonItemBinding, position: Int) {
        viewBinding.apply {
            cardManageGenericButton.setOnClickListener { onClick.actionCallback.invoke(type) }
        }
    }

    override fun getLayout(): Int = R.layout.admin_manage_generic_button_item

    override fun initializeViewBinding(view: View): AdminManageGenericButtonItemBinding = AdminManageGenericButtonItemBinding.bind(view)
}
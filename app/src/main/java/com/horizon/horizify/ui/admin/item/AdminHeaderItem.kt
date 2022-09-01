package com.horizon.horizify.ui.admin.item

import android.view.View
import com.horizon.horizify.R
import com.horizon.horizify.databinding.AdminHeaderItemBinding
import com.horizon.horizify.utils.ItemAction
import com.xwray.groupie.viewbinding.BindableItem

class AdminHeaderItem( val title : String, val onBack : ItemAction ) : BindableItem<AdminHeaderItemBinding>() {
    override fun bind(viewBinding: AdminHeaderItemBinding, position: Int) {
        with(viewBinding){
            txtTitle.text = title
            back.setOnClickListener { onBack.actionCallback.invoke() }
        }
    }

    override fun getLayout(): Int = R.layout.admin_header_item

    override fun initializeViewBinding(view: View): AdminHeaderItemBinding = AdminHeaderItemBinding.bind(view)
}
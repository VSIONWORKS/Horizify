package com.horizon.horizify.ui.menu.item

import android.view.View
import com.horizon.horizify.R
import com.horizon.horizify.databinding.MenuBodyItemBinding
import com.xwray.groupie.viewbinding.BindableItem

class MenuBodyItemFragment : BindableItem<MenuBodyItemBinding>() {
    override fun bind(viewBinding: MenuBodyItemBinding, position: Int) {
        with(viewBinding){

        }
    }

    override fun getLayout(): Int = R.layout.menu_body_item

    override fun initializeViewBinding(view: View): MenuBodyItemBinding = MenuBodyItemBinding.bind(view)
}
package com.horizon.horizify.ui.home.item

import android.view.View
import com.horizon.horizify.R
import com.horizon.horizify.databinding.HomeFooterItemBinding
import com.xwray.groupie.viewbinding.BindableItem

class HomeFooterItem : BindableItem<HomeFooterItemBinding>() {
    override fun bind(viewBinding: HomeFooterItemBinding, position: Int) {
        with(viewBinding){

        }
    }

    override fun getLayout(): Int = R.layout.home_footer_item

    override fun initializeViewBinding(view: View): HomeFooterItemBinding = HomeFooterItemBinding.bind(view)
}
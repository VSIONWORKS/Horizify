package com.horizon.horizify.ui.home.item

import android.view.View
import com.horizon.horizify.R
import com.horizon.horizify.databinding.HomeHeaderItemBinding
import com.xwray.groupie.viewbinding.BindableItem

class HomeHeaderItem : BindableItem<HomeHeaderItemBinding>() {
    override fun bind(viewBinding: HomeHeaderItemBinding, position: Int) {
        with(viewBinding){

        }
    }

    override fun getLayout(): Int = R.layout.home_header_item

    override fun initializeViewBinding(view: View): HomeHeaderItemBinding = HomeHeaderItemBinding.bind(view)
}
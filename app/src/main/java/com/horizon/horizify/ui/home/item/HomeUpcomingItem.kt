package com.horizon.horizify.ui.home.item

import android.view.View
import com.horizon.horizify.R
import com.horizon.horizify.databinding.HomeUpcomingItemBinding
import com.xwray.groupie.viewbinding.BindableItem

class HomeUpcomingItem : BindableItem<HomeUpcomingItemBinding>() {
    override fun bind(viewBinding: HomeUpcomingItemBinding, position: Int) {
        with(viewBinding) {

        }
    }

    override fun getLayout(): Int = R.layout.home_upcoming_item

    override fun initializeViewBinding(view: View): HomeUpcomingItemBinding = HomeUpcomingItemBinding.bind(view)
}
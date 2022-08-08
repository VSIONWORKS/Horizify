package com.horizon.horizify.ui.home.item

import android.view.View
import com.horizon.horizify.R
import com.horizon.horizify.databinding.HomeLocationCheckinItemBinding
import com.xwray.groupie.viewbinding.BindableItem

class HomeLocationCheckin : BindableItem<HomeLocationCheckinItemBinding>() {
    override fun bind(viewBinding: HomeLocationCheckinItemBinding, position: Int) {
        with(viewBinding) {

        }
    }

    override fun getLayout(): Int = R.layout.home_location_checkin_item

    override fun initializeViewBinding(view: View): HomeLocationCheckinItemBinding = HomeLocationCheckinItemBinding.bind(view)
}
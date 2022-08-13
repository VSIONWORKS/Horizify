package com.horizon.horizify.ui.home.item

import android.view.View
import com.horizon.horizify.R
import com.horizon.horizify.databinding.HomeLocationCheckinItemBinding
import com.horizon.horizify.utils.ItemAction
import com.xwray.groupie.viewbinding.BindableItem

class HomeLocationCheckIn(val onClickLocation: ItemAction, val onClickCheckIn: ItemAction) : BindableItem<HomeLocationCheckinItemBinding>() {
    override fun bind(viewBinding: HomeLocationCheckinItemBinding, position: Int) {
        with(viewBinding) {

            cardLocation.setOnClickListener {
                onClickLocation.actionCallback.invoke()
            }
            cardCheckIn.setOnClickListener {
                onClickCheckIn.actionCallback.invoke()
            }
        }
    }

    override fun getLayout(): Int = R.layout.home_location_checkin_item

    override fun initializeViewBinding(view: View): HomeLocationCheckinItemBinding = HomeLocationCheckinItemBinding.bind(view)
}
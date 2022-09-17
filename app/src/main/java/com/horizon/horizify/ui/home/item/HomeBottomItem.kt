package com.horizon.horizify.ui.home.item

import android.view.View
import com.horizon.horizify.R
import com.horizon.horizify.databinding.HomeBottomItemBinding
import com.horizon.horizify.utils.ItemAction
import com.xwray.groupie.viewbinding.BindableItem

class HomeBottomItem(val onClick: ItemAction) : BindableItem<HomeBottomItemBinding>() {
    override fun bind(viewBinding: HomeBottomItemBinding, position: Int) {
        with(viewBinding) {
            cardStory.setOnClickListener {
                onClick.actionCallback.invoke()
            }
            cardVision.setOnClickListener {
                onClick.actionCallback.invoke()
            }
            cardBelief.setOnClickListener {
                onClick.actionCallback.invoke()
            }
        }
    }

    override fun getLayout(): Int = R.layout.home_bottom_item

    override fun initializeViewBinding(view: View): HomeBottomItemBinding = HomeBottomItemBinding.bind(view)
}
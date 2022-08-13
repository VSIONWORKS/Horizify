package com.horizon.horizify.ui.home.item

import android.view.View
import com.horizon.horizify.R
import com.horizon.horizify.databinding.HomeBodyItemBinding
import com.horizon.horizify.utils.ItemAction
import com.xwray.groupie.viewbinding.BindableItem

class HomeBodyItem : BindableItem<HomeBodyItemBinding>() {

    var clickListener: ItemAction? = null

    override fun bind(viewBinding: HomeBodyItemBinding, position: Int) {
        with(viewBinding) {
//            homeTest.text = "Keneki Pogi"
//            btnTest.setOnClickListener {
//                clickListener?.actionCallback?.invoke()
//            }
        }
    }

    override fun getLayout(): Int = R.layout.home_body_item

    override fun initializeViewBinding(view: View): HomeBodyItemBinding = HomeBodyItemBinding.bind(view)
}
package com.horizon.horizify.ui.home.item

import android.view.View
import com.horizon.horizify.R
import com.horizon.horizify.databinding.FragmentHomeBinding
import com.horizon.horizify.utils.ItemAction
import com.xwray.groupie.viewbinding.BindableItem

class HomeBodyItem : BindableItem<FragmentHomeBinding>() {

    var clickListener: ItemAction? = null

    override fun bind(viewBinding: FragmentHomeBinding, position: Int) {
        with(viewBinding) {
            homeTest.text = "Keneki Pogi"
            btnTest.setOnClickListener {
                clickListener?.actionCallback?.invoke()
            }
        }
    }

    override fun getLayout(): Int = R.layout.fragment_home

    override fun initializeViewBinding(view: View): FragmentHomeBinding = FragmentHomeBinding.bind(view)
}
package com.horizon.horizify.ui.home.item

import android.view.View
import com.horizon.horizify.R
import com.horizon.horizify.databinding.HomeBodyRecyclerCardBinding
import com.horizon.horizify.ui.home.CardEnum
import com.horizon.horizify.utils.ItemActionWithPosition
import com.xwray.groupie.viewbinding.BindableItem

class HomeBodyRecyclerCard(val type: CardEnum , private val onClick: ItemActionWithPosition) : BindableItem<HomeBodyRecyclerCardBinding>() {
    override fun bind(viewBinding: HomeBodyRecyclerCardBinding, position: Int) {
        with(viewBinding) {
            cvHomeCard.setOnClickListener {
                onClick.actionCallback(type.ordinal)
            }
            val res = when(type) {
                CardEnum.CHECK_IN -> {
                    txtCard.text = "Check-in"
                    imgCard.setImageResource(R.drawable.ic_card_checkin)
                    R.drawable.card_gradient_1
                }
                CardEnum.GIVING -> {
                    txtCard.text = "Giving"
                    imgCard.setImageResource(R.drawable.ic_card_giving)
                    R.drawable.card_gradient_2
                }
                CardEnum.LOCATION -> {
                    txtCard.text = "Location"
                    imgCard.setImageResource(R.drawable.ic_card_location)
                    R.drawable.card_gradient_3
                }
                CardEnum.CONNECT -> {
                    txtCard.text = "Connect"
                    imgCard.setImageResource(R.drawable.ic_card_email)
                    R.drawable.card_gradient_4
                }
            }
            layoutFrame.setBackgroundResource(res)
        }
    }

    override fun getLayout(): Int = R.layout.home_body_recycler_card

    override fun initializeViewBinding(view: View): HomeBodyRecyclerCardBinding = HomeBodyRecyclerCardBinding.bind(view)
}
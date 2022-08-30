package com.horizon.horizify.ui.menu.item

import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.animation.AnimationUtils
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import com.horizon.horizify.R
import com.horizon.horizify.databinding.MenuBodyItemBinding
import com.horizon.horizify.ui.menu.MenuEnum
import com.horizon.horizify.utils.ItemActionWithValue
import com.xwray.groupie.viewbinding.BindableItem

class MenuBodyItemFragment(val onMenuClick: ItemActionWithValue<MenuEnum>, private val isAdmin: Boolean = true) : BindableItem<MenuBodyItemBinding>() {

    override fun bind(viewBinding: MenuBodyItemBinding, position: Int) {
        with(viewBinding){

            DELAY_MILLIS = 0

            layoutSv.isSmoothScrollingEnabled = true
            cardMenuAdmin.isVisible = false
            cardMenuProfile.isInvisible = true
            cardMenuCalendar.isInvisible = true
            cardMenuPreference.isInvisible = true
            cardMenuShare.isInvisible = true
            cardMenuPrivacyPolicy.isInvisible = true
            cardMenuTermsOfServices.isInvisible = true
            cardMenuAbout.isInvisible = true

            startMenuAnimation()
            setUpClickListener()
        }
    }

    override fun getLayout(): Int = R.layout.menu_body_item

    override fun initializeViewBinding(view: View): MenuBodyItemBinding = MenuBodyItemBinding.bind(view)

    private fun MenuBodyItemBinding.startMenuAnimation() {

        val handler = Handler(Looper.getMainLooper())

        with(root){
            with(R.anim.menu_card_slide_left) {

                if (isAdmin) {
                    handler.postDelayed({
                        cardMenuAdmin.startAnimation(AnimationUtils.loadAnimation(context, this))
                        cardMenuAdmin.isVisible = true
                    }, DELAY_MILLIS)

                    DELAY_MILLIS += DELAY_ADD
                }

                handler.postDelayed({
                    cardMenuProfile.startAnimation(AnimationUtils.loadAnimation(context, this))
                    cardMenuProfile.isInvisible = false
                }, DELAY_MILLIS)

                DELAY_MILLIS += DELAY_ADD
                handler.postDelayed({
                    cardMenuCalendar.startAnimation(AnimationUtils.loadAnimation(context, this))
                    cardMenuCalendar.isInvisible = false
                }, DELAY_MILLIS)

                DELAY_MILLIS += DELAY_ADD
                handler.postDelayed({
                    cardMenuPreference.startAnimation(AnimationUtils.loadAnimation(context, this))
                    cardMenuPreference.isInvisible = false
                }, DELAY_MILLIS)

                DELAY_MILLIS += DELAY_ADD
                handler.postDelayed({
                    cardMenuShare.startAnimation(AnimationUtils.loadAnimation(context, this))
                    cardMenuShare.isInvisible = false
                }, DELAY_MILLIS)

                DELAY_MILLIS += DELAY_ADD
                handler.postDelayed({
                    cardMenuPrivacyPolicy.startAnimation(AnimationUtils.loadAnimation(context, this))
                    cardMenuPrivacyPolicy.isInvisible = false
                }, DELAY_MILLIS)

                DELAY_MILLIS += DELAY_ADD
                handler.postDelayed({
                    cardMenuTermsOfServices.startAnimation(AnimationUtils.loadAnimation(context, this))
                    cardMenuTermsOfServices.isInvisible = false
                }, DELAY_MILLIS)

                DELAY_MILLIS += DELAY_ADD
                handler.postDelayed({
                    cardMenuAbout.startAnimation(AnimationUtils.loadAnimation(context, this))
                    cardMenuAbout.isInvisible = false
                }, DELAY_MILLIS)
            }
        }
    }

    private fun MenuBodyItemBinding.setUpClickListener() {
        cardMenuAdmin.setOnClickListener { onMenuClick.actionCallback.invoke(MenuEnum.ADMIN) }
        cardMenuProfile.setOnClickListener { onMenuClick.actionCallback.invoke(MenuEnum.PROFILE) }
        cardMenuCalendar.setOnClickListener { onMenuClick.actionCallback.invoke(MenuEnum.CALENDAR) }
        cardMenuPreference.setOnClickListener { onMenuClick.actionCallback.invoke(MenuEnum.PREFERENCES) }
        cardMenuShare.setOnClickListener { onMenuClick.actionCallback.invoke(MenuEnum.SHARE) }
        cardMenuPrivacyPolicy.setOnClickListener { onMenuClick.actionCallback.invoke(MenuEnum.POLICY) }
        cardMenuTermsOfServices.setOnClickListener { onMenuClick.actionCallback.invoke(MenuEnum.TERMS) }
        cardMenuAbout.setOnClickListener { onMenuClick.actionCallback.invoke(MenuEnum.ABOUT) }
    }

    companion object {
        var DELAY_MILLIS : Long = 0
        const val DELAY_ADD : Long = 200
    }
}
package com.horizon.horizify.ui.home.item

import android.graphics.Color
import android.text.SpannableString
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.view.View
import com.horizon.horizify.R
import com.horizon.horizify.databinding.HomeVerseItemBinding
import com.horizon.horizify.utils.TopAlignSuperscriptSpan
import com.xwray.groupie.viewbinding.BindableItem


class HomeVerseItem : BindableItem<HomeVerseItemBinding>() {
    override fun bind(viewBinding: HomeVerseItemBinding, position: Int) {
        with(viewBinding) {
            val verse = "1 For God so loved the world"
            txtVerse.text = createIndentedText(verse, 0, 0)
        }
    }

    private fun createIndentedText(text: String, marginFirstLine: Int, marginNextLines: Int): SpannableString? {
        val ss1 = SpannableString(text)
        ss1.setSpan(TopAlignSuperscriptSpan(0.1f), 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        ss1.setSpan(ForegroundColorSpan(Color.RED), 0, 1, 0)

        return ss1
    }

    override fun getLayout(): Int = R.layout.home_verse_item

    override fun initializeViewBinding(view: View): HomeVerseItemBinding = HomeVerseItemBinding.bind(view)
}
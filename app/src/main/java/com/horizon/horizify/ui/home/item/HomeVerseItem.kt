package com.horizon.horizify.ui.home.item

import android.graphics.Color
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.style.ForegroundColorSpan
import android.text.style.SuperscriptSpan
import android.view.View
import com.horizon.horizify.R
import com.horizon.horizify.databinding.HomeVerseItemBinding
import com.xwray.groupie.viewbinding.BindableItem


class HomeVerseItem : BindableItem<HomeVerseItemBinding>() {
    override fun bind(viewBinding: HomeVerseItemBinding, position: Int) {
        with(viewBinding){
            val verse = "1 For God so loved the world"
            txtVerse.text = createIndentedText(verse,0,0)
        }
    }

    private fun createIndentedText(text: String, marginFirstLine: Int, marginNextLines: Int): SpannableString? {
        val ss1 = SpannableString(text)
        ss1.setSpan(TopAlignSuperscriptSpan(0.1f), 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        ss1.setSpan(ForegroundColorSpan(Color.RED), 0, 1, 0);

        return ss1
    }

    override fun getLayout(): Int = R.layout.home_verse_item

    override fun initializeViewBinding(view: View): HomeVerseItemBinding = HomeVerseItemBinding.bind(view)
}

class TopAlignSuperscriptSpan : SuperscriptSpan {
    //divide superscript by this number
    protected var fontScale = 2

    //shift value, 0 to 1.0
    protected var shiftPercentage = 0f

    //doesn't shift
    internal constructor() {}

    //sets the shift percentage
    internal constructor(shiftPercentage: Float) {
        if (shiftPercentage > 0.0 && shiftPercentage < 1.0) this.shiftPercentage = shiftPercentage
    }

    override fun updateDrawState(tp: TextPaint) {
        //original ascent
        val ascent = tp.ascent()

        //scale down the font
        tp.textSize = (tp.textSize + 25) / fontScale

        //get the new font ascent
        val newAscent = tp.fontMetrics.ascent

        //move baseline to top of old font, then move down size of new font
        //adjust for errors with shift percentage
        tp.baselineShift += (ascent - ascent * shiftPercentage
                - (newAscent - newAscent * shiftPercentage)).toInt()
    }

    override fun updateMeasureState(tp: TextPaint) {
        updateDrawState(tp)
    }
}
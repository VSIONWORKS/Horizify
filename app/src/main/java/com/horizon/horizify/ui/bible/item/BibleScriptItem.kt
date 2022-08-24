package com.horizon.horizify.ui.bible.item

import android.content.Context
import android.text.SpannableString
import android.text.Spanned
import android.text.TextUtils
import android.text.style.ForegroundColorSpan
import android.view.View
import androidx.core.content.ContextCompat
import com.horizon.horizify.R
import com.horizon.horizify.databinding.BibleScriptItemBinding
import com.horizon.horizify.ui.bible.model.Chapter
import com.horizon.horizify.utils.TopAlignSuperscriptSpan
import com.xwray.groupie.viewbinding.BindableItem

class BibleScriptItem(val bookIndex: Int, val currentChapterIndex: Int, val chapter: Chapter, val context: Context)  :BindableItem<BibleScriptItemBinding>() {
    override fun bind(viewBinding: BibleScriptItemBinding, position: Int) {
        with(viewBinding){
            txtVerses.text = BibleItem.EMPTY_STRING
            var strVerse = txtVerses.text
            chapter.verses.forEach {
                val str = "${it.verse} ${it.script}\n"
                val spannedStr = formatVerse(it.verse, str)
                strVerse = TextUtils.concat(strVerse, spannedStr)
            }
            txtVerses.text = strVerse

            txtVerses.setOnTouchListener { view, motionEvent ->
                view.parent.requestDisallowInterceptTouchEvent(false)
                false
            }
//            txtVerses.movementMethod = ScrollingMovementMethod.getInstance()
        }
    }

    override fun getLayout(): Int = R .layout.bible_script_item

    override fun initializeViewBinding(view: View): BibleScriptItemBinding = BibleScriptItemBinding.bind(view)

    private fun formatVerse(verseNo: String, text: String): SpannableString? {

        val endCount = verseNo.count()
        val spannedString = SpannableString(text)

        spannedString.setSpan(TopAlignSuperscriptSpan(0.1f), 0, endCount, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        spannedString.setSpan(ForegroundColorSpan(ContextCompat.getColor(context, R.color.verse)), 0, endCount, 0);

        return spannedString
    }
}
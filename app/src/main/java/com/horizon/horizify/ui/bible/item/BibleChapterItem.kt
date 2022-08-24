package com.horizon.horizify.ui.bible.item

import android.view.View
import com.horizon.horizify.R
import com.horizon.horizify.databinding.BibleChapterItemBinding
import com.horizon.horizify.extensions.setTextColorRes
import com.horizon.horizify.utils.ItemActionWithValue
import com.xwray.groupie.viewbinding.BindableItem

class BibleChapterItem(private val currentChapter: String, private val chapter: String, private val onClick: ItemActionWithValue<String>) : BindableItem<BibleChapterItemBinding>() {
    override fun bind(viewBinding: BibleChapterItemBinding, position: Int) {
        with(viewBinding) {
            textChapterSelected.text = chapter
            btnChapter.setOnClickListener {
                onClick.actionCallback(chapter)
            }
            if (currentChapter == chapter) textChapterSelected.setTextColorRes(R.color.status_Bar)
            else textChapterSelected.setTextColorRes(R.color.semi_light_grey)
        }
    }

    override fun getLayout(): Int = R.layout.bible_chapter_item

    override fun initializeViewBinding(view: View): BibleChapterItemBinding = BibleChapterItemBinding.bind(view)
}
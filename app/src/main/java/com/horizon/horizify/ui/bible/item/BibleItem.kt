package com.horizon.horizify.ui.bible.item

import android.content.Context
import android.graphics.Color
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.horizon.horizify.R
import com.horizon.horizify.databinding.BibleBottomsheetBinding
import com.horizon.horizify.databinding.BibleFragmentBinding
import com.horizon.horizify.extensions.layoutInflater
import com.horizon.horizify.ui.bible.model.BibleModel
import com.horizon.horizify.ui.bible.viewModel.BibleViewModel
import com.horizon.horizify.utils.BindableItemObserver
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.viewbinding.BindableItem

class BibleItem(val viewModel: BibleViewModel, val context: Context) : BindableItem<BibleFragmentBinding>() {

    private val bible = viewModel.currentBible()

    private val groupAdapter = GroupAdapter<GroupieViewHolder>()

    var bibleTest by BindableItemObserver(BibleModel())

    override fun bind(viewBinding: BibleFragmentBinding, position: Int) {
        with(viewBinding) {
            if (bibleTest.books.isNotEmpty()) {
                bibleBook.text = bibleTest.books.first().book
                bibleVersionDescription.text = bibleTest.versionLong

                setUpSelectBookBottomSheet()
            }
        }
    }

    override fun getLayout(): Int = R.layout.bible_fragment
    override fun initializeViewBinding(view: View): BibleFragmentBinding = BibleFragmentBinding.bind(view)

    private fun BibleFragmentBinding.setUpSelectBookBottomSheet() {
        imgSelectBook.setOnClickListener {
            val dialog = BottomSheetDialog(root.context)
//            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)

            val sheetBinding = BibleBottomsheetBinding.inflate(context.layoutInflater)
            dialog.setContentView(sheetBinding.root)
            sheetBinding.bookBottomsheet.setBackgroundColor(Color.TRANSPARENT)
            dialog.window?.setBackgroundDrawableResource(R.color.transparent)
            sheetBinding.bookBottomsheet.setBackgroundColor(context.resources.getColor(android.R.color.transparent))

            dialog.behavior.state = BottomSheetBehavior.STATE_EXPANDED


            with(sheetBinding) {
                rvBooks.apply {
                    adapter = groupAdapter.apply {
                        bibleTest.books.forEach { it ->
                            add(BibleBookItem(it.book))
                        }
                    }
                    layoutManager = LinearLayoutManager(context)
                }
            }
            dialog.show()


//            var bottomSheetBehavior = BottomSheetBehavior.from(sheetBinding.bookBottomsheet.rootView)
//            bottomSheetBehavior.expandedOffset = 100
//
//            if (bottomSheetBehavior.state != BottomSheetBehavior.STATE_EXPANDED) {
//                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED)
//            } else {
//                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED)
//            }
        }
    }
}
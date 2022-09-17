package com.horizon.horizify.base

import com.jay.widget.StickyHeaders
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.Section

class RootAdapter : GroupAdapter<GroupieViewHolder>(), StickyHeaders {
    override fun isStickyHeader(position: Int): Boolean = getItem(position) is Sticky
    infix fun attach(section: Section): RootAdapter {
        add(section)
        return this
    }
}

interface Sticky

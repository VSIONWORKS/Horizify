package com.horizon.horizify.base

import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.Section

class RootAdapter : GroupAdapter<GroupieViewHolder>() {
    infix fun attach(section: Section): RootAdapter {
        add(section)
        return this
    }
}
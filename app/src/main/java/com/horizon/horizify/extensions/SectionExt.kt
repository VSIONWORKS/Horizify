package com.horizon.horizify.extensions

import com.xwray.groupie.Group
import com.xwray.groupie.Section

fun Section.setBody(vararg group: Group): Section {
    update(group.toList())
    return this
}
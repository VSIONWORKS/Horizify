package com.horizon.horizify.utils

import com.xwray.groupie.viewbinding.BindableItem
import kotlin.reflect.KProperty

class BindableItemObserver<T>(private var value: T) {

    operator fun getValue(thisRef: BindableItem<*>, prop: KProperty<*>): T {
        return value
    }

    operator fun setValue(thisRef: BindableItem<*>, prop: KProperty<*>, value: T) {
        this.value = value
        thisRef.notifyChanged()
    }
}
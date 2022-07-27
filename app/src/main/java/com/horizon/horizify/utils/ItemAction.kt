package com.horizon.horizify.utils

class ItemAction(
    val actionCallback: (() -> Unit)
)

class ItemActionWithPosition(
    val actionCallback: ((position: Int) -> Unit)
)

class ItemActionWithValue<T>(
    val actionCallback: ((value: T) -> Unit)
)
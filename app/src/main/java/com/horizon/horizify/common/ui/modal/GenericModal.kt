package com.horizon.horizify.common.ui.modal

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.View
import android.view.Window
import com.horizon.horizify.extensions.setDefaultDialogDimension

class GenericModal(context: Context, val view: View): Dialog(context) {
    init {
        this.apply {
            requestWindowFeature(Window.FEATURE_NO_TITLE)
            window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            setCancelable(true)
            setContentView(view)
            setDefaultDialogDimension()
        }
    }
}
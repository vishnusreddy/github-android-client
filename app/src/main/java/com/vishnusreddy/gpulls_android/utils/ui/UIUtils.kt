package com.vishnusreddy.gpulls_android.utils.ui

import android.app.Activity
import android.content.Context
import android.view.inputmethod.InputMethodManager

class UIUtils {
    fun hideKeyboard(context: Context?) {
        if (context != null && (context as Activity).currentFocus != null) {
            val inputManager =
                context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputManager.hideSoftInputFromWindow(context.currentFocus!!.windowToken, 0)
        }
    }
}
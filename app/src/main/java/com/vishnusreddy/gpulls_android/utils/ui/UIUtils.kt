package com.vishnusreddy.gpulls_android.utils.ui

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.google.android.material.snackbar.Snackbar

object UIUtils {
    fun hideKeyboard(context: Context?) {
        if (context != null && (context as Activity).currentFocus != null) {
            val inputManager =
                context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputManager.hideSoftInputFromWindow(context.currentFocus!!.windowToken, 0)
        }
    }

    fun showSnackbar(view: View, message: Int) {
        Snackbar.make(view, message, Snackbar.LENGTH_SHORT)
            .show()
    }
}
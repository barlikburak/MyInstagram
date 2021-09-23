package com.bb.nst.utils.display

import android.content.Context
import android.util.Log
import android.widget.Toast

object Toaster {
    /**
     * In logcat and to show it to the user.
     */
    fun show(context: Context, text: String?, tag: String? = null) {
        tag?.let {
            Log.e(it, text.toString())
            println("$it: $text")
        }
        Toast.makeText(context, text ?: "ERROR!", Toast.LENGTH_LONG).show()
    }
}
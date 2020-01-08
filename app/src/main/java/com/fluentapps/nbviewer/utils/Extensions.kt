package com.fluentapps.nbviewer.utils

import android.content.ContentResolver
import android.net.Uri
import android.provider.OpenableColumns
import androidx.databinding.ViewDataBinding

/**
 * Extension to get filename from Uri
 */
fun ContentResolver.queryName(uri: Uri): String {
    var name = ""
    this.query(uri, null, null, null, null)?.apply {
        val nameIndex = getColumnIndex(OpenableColumns.DISPLAY_NAME)
        moveToFirst()
        name = getString(nameIndex)
        close()
    }
    return name
}

inline fun <T : ViewDataBinding> T.executeAfter(block: T.() -> Unit) {
    block()
    executePendingBindings()
}
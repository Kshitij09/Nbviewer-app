package com.fluentapps.nbviewer.utils

import android.content.ContentResolver
import android.net.Uri
import android.provider.OpenableColumns

fun ContentResolver.queryName(uri: Uri): String {
    var name: String = ""
    this.query(uri, null, null, null, null)?.apply {
        val nameIndex = getColumnIndex(OpenableColumns.DISPLAY_NAME)
        moveToFirst()
        name = getString(nameIndex)
        close()
    }
    return name
}
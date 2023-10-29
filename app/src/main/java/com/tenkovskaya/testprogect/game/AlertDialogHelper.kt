package com.tenkovskaya.testprogect.game

import android.app.AlertDialog
import android.content.Context

class AlertDialogHelper(private val context: Context) {
    fun showAlertDialog(title: String, message: String) {
        AlertDialog.Builder(context)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton("OK") { dialog, which ->
                dialog.dismiss()
            }
            .show()
    }
}

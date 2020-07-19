package com.carwale.covidapp.utils.dialogs

import android.content.Context
import android.content.DialogInterface
import com.carwale.covidapp.R
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class DialogBuilder {
    companion object {
        fun showDialog(context: Context, titleString: String, listener: DialogListener) {
            val dialogBuilder = MaterialAlertDialogBuilder(context)
                .setTitle(titleString)
//            .setView(dialogView)
                .setNegativeButton(context.resources.getString(R.string.no), null)
                .setPositiveButton(context.resources.getString(R.string.yes), null)
                .setCancelable(false)
                .create()
            dialogBuilder.setOnShowListener { dialogInterface ->
                listener.onDialogShow(dialogInterface)
                dialogBuilder.getButton(DialogInterface.BUTTON_POSITIVE).setOnClickListener {
                    dialogBuilder.dismiss()
                    listener.onPositiveClick(it)
                }
                dialogBuilder.getButton(DialogInterface.BUTTON_NEGATIVE).setOnClickListener {
                    dialogBuilder.dismiss()
                    listener.onNegativeClick(it)
                }
            }
            dialogBuilder.show()
        }
    }
}

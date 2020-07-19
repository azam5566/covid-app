package com.carwale.covidapp.utils.dialogs

import android.content.DialogInterface
import android.view.View
import java.util.*

interface DialogListener {
    fun onPositiveClick(view: View) {

    }
    fun onNegativeClick(view: View) {

    }
    fun onDialogShow(dialogInterface: DialogInterface) {

    }

    fun onDateSelection(cal: Calendar) {

    }
}
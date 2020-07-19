package com.carwale.covidapp.utils.extensions

import android.content.Context
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.android.material.radiobutton.MaterialRadioButton


fun View.setViewTextColor(ctx: Context, colorCode: Int, vararg views: View) {

    for (vw in views) {
        when (vw) {
            is RadioButton -> {
                vw.setTextColor(ContextCompat.getColor(ctx, colorCode))
            }
            is MaterialRadioButton -> {
                vw.setTextColor(ContextCompat.getColor(ctx, colorCode))
            }
            is TextView -> {
                vw.setTextColor(ContextCompat.getColor(ctx, colorCode))
            }
            is Button -> {
                vw.setTextColor(ContextCompat.getColor(ctx, colorCode))
            }
        }
    }
}


/**
 *  select_view -> pass single select view and its drawable Int resource
 *  unselect_view -> pass multiple views and its common unselect drawable Int resource
 */
fun View.setSingleToManyViewsBackground(
    drawableSelect: Int,
    drawableUnSelect: Int,
    selectedView: View,
    vararg unselectedViews: View
) {

    selectedView.setViewsBackground(drawableSelect, selectedView)

    for (vw in unselectedViews)
        vw.setViewsBackground(drawableUnSelect, vw)
}


/**
 * select_view_text_color -> pass single select view and its text color Int resource
 * unselect_view_text_color -> pass multiple views and its common unselect text color Int resource
 */
fun View.setSingleToManyViewsTextColors(
    context: Context,
    selectColor: Int,
    unselectColor: Int,
    selectedView: View,
    vararg unselectedViews: View
) {

    selectedView.setViewTextColor(context, selectColor, selectedView)

    for (vw in unselectedViews)
        vw.setViewTextColor(context, unselectColor, vw)
}


fun View.setViewsBackground(res: Int, vararg views: View) {
    for (view in views) {
        view.setBackgroundResource(res)
    }
}


//----------------------*-*-*---------------------//
/*Toast Messages Utils*/

fun AppCompatActivity.showMessage(message: String, time: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, message, time).show()
}

fun AppCompatActivity.showLongMessage(message: String, time: Int = Toast.LENGTH_LONG) {
    Toast.makeText(this, message, time).show()
}

fun Fragment.showMessage(message: String, time: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this.activity, message, time).show()
}

fun Fragment.showLongMessage(message: String, time: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this.activity, message, time).show()
}

//----------------------*-*-*---------------------//
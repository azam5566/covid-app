package com.carwale.covidapp.utils.permission

import android.app.Activity
import android.content.Intent
import android.provider.ContactsContract


class UriUtils {

    companion object{

        fun pickContact(activity:Activity, resultCode:Int) {
            val contactPickerIntent = Intent(Intent.ACTION_PICK, ContactsContract.CommonDataKinds.Phone.CONTENT_URI)
            activity.startActivityForResult(contactPickerIntent, resultCode)
        }

    }

}

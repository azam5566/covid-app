package com.carwale.covidapp.utils

class Constants {

    interface Permissions {
        companion object {
            const val READ_CONTACT_REQ = 21
            const val RESULT_PICK_CONTACT = 22
        }
    }

    interface SharedPrefTags {
        companion object {
            const val myPrefKey: String = "PreferenceCOVID"
            const val TOKEN: String = "token_key"
            const val SELECTED_LOCALE = "SELECTED_LOCALE"
            const val  USER_ID =  "USER_ID"
            const val  USER_NAME =  "USER_NAME"
            const val ONBOARDING_STATUS = "ONBOARDING_STATUS"
            const val LAST_ONLINE_TIME = "LAST_ONLINE_TIME"
            const val GAP = "AIzaSyCuEVBMkxsXHhDihEXqSB8K3yozRWpxrvA"
        }
    }

    interface TrueFalseStrings {
        companion object {
            const val TRUE = "true"
            const val FALSE = "false"
        }
    }

    interface ResultValues {
        companion object {

            const val API_SUCCESS_RESPONSE = 200
            const val API_INCOMPLETE_DATA_TRANSACTION = 207
            const val INSERT_QUERY_UNSUCCESS_RES: Long = -1
            const val INSERT_QUERY_RESULT_UNSUCCESS_LONG: Long = 9
            const val DELETE_QUERY_RESULT_SUCCESS: Int = 1
        }
    }

    interface Location{
        companion object{
            const val COUNTRY_NAME = "COUNTRY_NAME"
            const val COUNTRY_CODE = "COUNTRY_CODE"
            const val ADDRESS = "ADDRESS"
            const val LAT = "LAT"
            const val LONG = "LONG"
            const val NAME = "NAME"
        }
    }

}
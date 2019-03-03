package com.interview.safeboda.utils.helper

import android.content.Context
import android.content.SharedPreferences
import com.interview.safeboda.common.Constants.Companion.TOKEN
import com.interview.safeboda.common.Constants.Companion.DEPARTURE_AIRPORT
import com.interview.safeboda.common.Constants.Companion.PREF_FILENAME

class Pref(context:Context) {
    val prefs: SharedPreferences = context.getSharedPreferences(PREF_FILENAME, 0)



    var departureAirport: String
        get() = prefs.getString(DEPARTURE_AIRPORT, "")
        set(value) = prefs.edit().putString(DEPARTURE_AIRPORT, value).apply()


    var token: String
        get() = prefs.getString(TOKEN, "")
        set(value) = prefs.edit().putString(TOKEN, value).apply()
}
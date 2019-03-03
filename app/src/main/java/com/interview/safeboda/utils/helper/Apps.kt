package com.interview.safeboda.utils.helper

import android.app.Application

class Apps:Application() {

    companion object {
        lateinit var aiport: Pref

    }


    override fun onCreate() {
        aiport = Pref(applicationContext)
        super.onCreate()

        //RequestToken
    }
}
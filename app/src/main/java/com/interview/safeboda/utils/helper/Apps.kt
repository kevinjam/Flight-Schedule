package com.interview.safeboda.utils.helper

import android.app.Application

class Apps:Application() {

    companion object {
        lateinit var aiport: AiportStart

    }


    override fun onCreate() {
        aiport = AiportStart(applicationContext)
        super.onCreate()

        //RequestToken
    }
}
package com.interview.safeboda.activities.splash

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.interview.safeboda.utils.helper.Apps
import com.interview.safeboda.activities.schedules.MainActivity
import com.interview.safeboda.modelLayer.model.airport.RequestToken
import com.interview.safeboda.common.disposedBy
import com.interview.safeboda.utils.helper.Helper
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import kotlinx.coroutines.*
import java.io.IOException


class SplashScreen : AppCompatActivity() {

    //new way
    private val presenter = SplashPresenter()
    private var composit = CompositeDisposable()

    val uiDispatcher: CoroutineDispatcher = Dispatchers.Main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        composit = CompositeDisposable()

        //reset token
        Apps.aiport.token = ""
        //
        println("Tken on Create${ Apps.aiport.token}")
        uiDispatcher
        if(Helper.isConnectedToInternet(this)){
            GlobalScope.launch {
                delay(500)
                //testing
                //gotoMain()  //TESTING PURPOSE
                //request Token
                loadToken()
            }
        }else{
            //not network .. call snakbar
            println("No inetenet")
        }


    }

    @SuppressLint("CheckResult")
    private fun loadToken() {
        presenter.getToken().observeOn(AndroidSchedulers.mainThread())
            .subscribe({ token ->
                println("😜 Response is $token")
                checkToken(token)
            }, { error ->
                println("👺 an Error occurred : ${error.localizedMessage}")
                val e = error; IOException("An Unknow network occurred")
                println("👺 an Error occurred : ${e.localizedMessage}")
            }).disposedBy(composit)
    }

    private fun checkToken(token: RequestToken?) {
        if (token != null) {
            //save Token Using viewModel
            gotoMain(token.access_token)
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        composit.clear()
    }

    private fun gotoMain(access_token: String) {
        Apps.aiport.token = access_token
        println("Tken on Create$access_token")

        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
    }


}

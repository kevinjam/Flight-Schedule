package com.interview.safeboda.activities.splash

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.interview.safeboda.utils.helper.Apps
import com.interview.safeboda.activities.schedules.MainActivity
import com.interview.safeboda.modelLayer.model.airport.RequestToken
import com.interview.safeboda.common.disposedBy
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

        //request Token

        uiDispatcher

GlobalScope.launch {
    delay(1000)

    //testing
    gotoMain()
   // loadToken()

}


    }

    @SuppressLint("CheckResult")
    private fun loadToken() {
        presenter.getToken().observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                    token->
                println("😜 Response is $token")
                checkToken(token)
            },{
                error->
                println("👺 an Error occurred : ${ error.localizedMessage}")

                val e = error; IOException("An Unknow network occurred")
                println("👺 an Error occurred : ${ e.localizedMessage}")
                Apps.aiport.token=""
                gotoMain()


            }).disposedBy(composit)
    }

    private fun checkToken(token: RequestToken?) {
        if(token != null){
            //save Token Using viewModel
            Apps.aiport.token = token.access_token
            gotoMain()
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        composit.clear()
    }

    private fun gotoMain() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }



}

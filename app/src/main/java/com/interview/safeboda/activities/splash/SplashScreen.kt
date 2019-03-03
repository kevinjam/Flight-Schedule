package com.interview.safeboda.activities.splash

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.google.android.material.snackbar.Snackbar
import com.interview.safeboda.R
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
    var checking:String?= null


    val uiDispatcher: CoroutineDispatcher = Dispatchers.Main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        composit = CompositeDisposable()

        val parent_view:View = findViewById(android.R.id.content)

        //reset token
        //

        println("Tken on Create${ Apps.aiport.token}")
        uiDispatcher
        if(Helper.isConnectedToInternet(this)){
            GlobalScope.launch {
                delay(1000)
                //testing
                //request Token
//                loadToken()
                gotoMain("")  //TESTING PURPOSE

            }

        }else{

            //not network .. call snakbar
            snakbar(parent_view, getString(R.string.no_internet),"Try again")

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

               // gotoMain( Apps.aiport.token)
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

    private fun
            gotoMain(access_token: String) {
        Apps.aiport.token = access_token
        println("Tken on Create$access_token")
        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
    }


    fun snakbar(view: View, message:String, tryagain:String){
        val snackbar = Snackbar
            .make(view,  message, Snackbar.LENGTH_LONG)
            .setAction(tryagain) {

                loadToken()
            }

        // Changing message text color
        snackbar.setActionTextColor(Color.WHITE)
        //Changing action button text color
        snackbar.duration = Snackbar.LENGTH_INDEFINITE
        snackbar.view.setBackgroundColor(Color.GRAY)
        snackbar.show()




    }

}

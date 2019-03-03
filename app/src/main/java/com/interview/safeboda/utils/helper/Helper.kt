package com.interview.safeboda.utils.helper

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.net.ConnectivityManager
import android.os.Build
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.view.WindowManager
import com.google.android.material.snackbar.Snackbar
import com.google.gson.GsonBuilder
import com.interview.safeboda.BuildConfig
import com.interview.safeboda.R
import com.interview.safeboda.utils.AuthenticationInterceptor
import com.interview.safeboda.common.Constants.Companion.BASE_URL
import com.interview.safeboda.common.Constants.Companion.DATEFORMATER
import com.interview.safeboda.common.Constants.Companion.HOUR_MINUTES
import com.interview.safeboda.common.Constants.Companion.SCHEDULE_DATE
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

object Helper {

    fun toStringGson() = GsonBuilder().setPrettyPrinting().create()
    var gson = GsonBuilder()
        .setLenient()
        .create()

    /**
     * set timeout
     */

    val okHttpClient = OkHttpClient.Builder()
        .readTimeout(60, TimeUnit.SECONDS)
        .connectTimeout(60, TimeUnit.SECONDS)
        .build()


    /**
     * Retrofit Calls
     */

    private val httpClient = OkHttpClient.Builder()

    private val builder = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())

    private var retrofit = builder.build()

    fun <S> createService(serviceClass: Class<S>): S {

        if (Apps.aiport.token.isBlank()){
            //set a null

            return createService(serviceClass, null)
        }else{

//            return createService(serviceClass, Apps.aiport.token.replace("\\s",""))
                    return createService(serviceClass, "mqg2vtghsdunwretu2vqf555") //TESTING

        }

      //  return createService(serviceClass, Apps.aiport.token)
    }

    fun <S> createService(
        serviceClass: Class<S>, authToken: String?
    ): S {
        if (!TextUtils.isEmpty(authToken)) {
            val interceptor = AuthenticationInterceptor(authToken!!)

            if (!httpClient.interceptors().contains(interceptor)) {
                httpClient.addInterceptor(interceptor)
                builder.client(httpClient.build())
                retrofit = builder.build()
            }
        }

        return retrofit.create(serviceClass)
    }


    /**
     * @msg:message pass by the logs
     * this Only on Debug mode
     * Removed to Production
     */
    fun log(msg:String){
        if(BuildConfig.DEBUG){
            Log.e(javaClass.simpleName, msg)
        }else{
        }

    }

    @SuppressLint("SimpleDateFormat")
    fun dateConversion(isoTime: String): String? {
        val simpFormater = SimpleDateFormat(DATEFORMATER)
        val convertedDate: Date?
        var formattedDate: String? = null
        try {
            convertedDate = simpFormater.parse(isoTime)
            formattedDate = SimpleDateFormat(HOUR_MINUTES).format(convertedDate)
        } catch (e: ParseException) {
            e.printStackTrace()
        }

        return formattedDate
    }


    //region SCHEDULE DATE CONVERT
    fun scheduleDate(selectedDate: Long):String {
        val newFormat = SimpleDateFormat(SCHEDULE_DATE)
        return newFormat.format(Date(selectedDate!!))
    }
    //endregion


    //region System Bar Color
    fun setSystemBarColor(act: Activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val window = act.window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.statusBarColor = act.resources.getColor(R.color.colorPrimaryDark)
        }
    }
    //endregion



    //region CHECK INTERNET
    fun isConnectedToInternet(context: Context):Boolean{
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = cm.activeNetworkInfo
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting
    }
    //endregion


    @SuppressLint("WrongConstant")
    fun snakbar(view: View, message:String, tryagain:String){
        val snackbar = Snackbar
            .make(view,  message, Snackbar.LENGTH_LONG)
            .setAction(tryagain) {


            }

        // Changing message text color
        snackbar.setActionTextColor(Color.WHITE)
        //Changing action button text color
        snackbar.duration = Snackbar.LENGTH_INDEFINITE
        snackbar.view.setBackgroundColor(Color.GRAY)
        snackbar.show()
    }
    fun currentDate() :String{
        val df = SimpleDateFormat(SCHEDULE_DATE)

        return df.format(Calendar.getInstance().time)
    }
}
package com.interview.safeboda.activities.airport

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.google.android.material.snackbar.Snackbar
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.interview.safeboda.common.AirportCallback
import com.interview.safeboda.R
import com.interview.safeboda.activities.airport.recycler.AirportAdapter
import com.interview.safeboda.activities.airport.viewmodel.AirportViewModel
import com.interview.safeboda.activities.schedules.SchedulePresenter
import com.interview.safeboda.activities.splash.SplashPresenter
import com.interview.safeboda.modelLayer.model.airport.Airport
import com.interview.safeboda.modelLayer.model.airport.Payload
import com.interview.safeboda.common.Constants.Companion.AIRPORT_ORIGIN_DEPARTURE
import com.interview.safeboda.utils.helper.Helper
import com.interview.safeboda.common.disposedBy
import com.interview.safeboda.utils.helper.Apps
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.airlinelayout.*
import kotlinx.android.synthetic.main.toolbar.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.IOException


class AirportActivity : AppCompatActivity(), AirportCallback, View.OnClickListener{



    var composit= CompositeDisposable()
    lateinit var airpline:List<Airport>
     var airportObject:Airport?= null
    private var presenter= SchedulePresenter()
private var parent_view:View?=null
    private var splashscreen=SplashPresenter()

    override fun onMethodCallback(view: View,airport: Airport?) {

        println("😜 😍Method Call Back")
        println(" airportOrigin +++++  $airport ")

        airportObject = airport!!
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.airlinelayout)
        parent_view = findViewById(android.R.id.content)


        Helper.log("Airpot ${ Apps.aiport.token}")

        setSupportActionBar(toolbar)
        supportActionBar!!.title = "Select Flight"
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        attachUI()

        if (Helper.isConnectedToInternet(this)){
            GlobalScope.launch {
                requestAirport()
            }
        }else{
            progress_circular.visibility = View.GONE
            Helper.snakbar(parent_view!!, getString(R.string.no_internet),getString(R.string.try_again))
        }





    }




    fun requestAirport(){
        println("Start Requesting the Flight")
        presenter.airportRx()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                    response->
                Helper.log("Response ---$response")
                handlerResponse(response)
            },{
                    error->
                Helper.log("Errror ---$error")
                airplaine_recycler.visibility = View.GONE
                progress_circular.visibility = View.GONE

                if(error.localizedMessage.contains("401")){
                    no_network_message.visibility = View.GONE

                    Helper.snakbar(parent_view!!, getString(R.string.secret_key_error), "")

                }else{
                    Helper.snakbar(parent_view!!, error.localizedMessage!!, "")
                    no_network_message.visibility=View.VISIBLE

                }



            }).disposedBy(composit)

    }





    private fun attachUI() {
        btn_back.setOnClickListener(this)

        val linearLayoutManager = LinearLayoutManager(this)
        val dividerItemDecoration = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        airplaine_recycler.layoutManager = linearLayoutManager
        airplaine_recycler.setHasFixedSize(true)
        airplaine_recycler.addItemDecoration(dividerItemDecoration)
        airplaine_recycler.visibility = View.GONE
        progress_circular.visibility = View.VISIBLE


    }




    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.btn_back ->{
//                Helper.log("airportOrigin +++++  $airportObject ")

                if (airportObject != null){
                    val result = Intent()
                    result.putExtra(AIRPORT_ORIGIN_DEPARTURE, airportObject)
                    setResult(Activity.RESULT_OK, result)
                    finish()
                }

            }

            else -> {
            }
        }
    }


    private fun handlerResponse(response: Payload?) {
        airpline = response!!.airportResource.airport.airport

        Helper.log("Response --- $response")
        val list = airpline as ArrayList<Airport>
        airplaine_recycler.visibility = View.VISIBLE
        progress_circular.visibility = View.GONE
         val adapter = AirportAdapter(list, this@AirportActivity)
        airplaine_recycler.adapter = adapter


        adapter.setOnItemClickListener(object : AirportAdapter.OnItemClickListener {
            override fun onItemClick(view: View, obj: Airport, position: Int) {
                Helper.snakbar(parent_view!!,obj.name.name.countryName ,"")


            }
        })

    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        } else {
            Toast.makeText(applicationContext, item.title, Toast.LENGTH_SHORT).show()
        }
        return super.onOptionsItemSelected(item)
    }




}

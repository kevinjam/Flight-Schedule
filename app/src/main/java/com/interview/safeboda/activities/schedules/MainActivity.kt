package com.interview.safeboda.activities.schedules

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.interview.safeboda.utils.helper.Apps
import com.interview.safeboda.R
import com.interview.safeboda.activities.airport.FlightPresenter
import com.interview.safeboda.utils.helper.fonts.TextView_Roboto_Medium
import com.interview.safeboda.modelLayer.model.airport.Airport
import com.interview.safeboda.modelLayer.model.schedule.Schedule
import com.interview.safeboda.common.Constants.Companion.AIRPORT_ORIGIN_DEPARTURE
import com.interview.safeboda.common.disposedBy
import com.interview.safeboda.utils.helper.Helper
import com.interview.safeboda.viewmodel.ShareFlightViewModel
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.toolbar.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*


class MainActivity : AppCompatActivity() {

 lateinit var adapter: ScheduleAdapter

    private val DEPARTURE_AIRPORT_CODE = 8
    private val ARRIVAL_AIRPORT_CODE = 10

     var dateSelected :String?= null

    private val presenter = FlightPresenter()

    var composit = CompositeDisposable()

    private var mViewModel: ShareFlightViewModel? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initToolbar()
        progress_bar.visibility = View.GONE
        lyt_no_connection.visibility = View.VISIBLE

        lyt_no_connection.setOnClickListener {
            progress_bar.visibility = View.VISIBLE
            lyt_no_connection.visibility = View.GONE

        }

        //initiate RecyclerView
        recycler_schedule.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(this)
        img_departure.setOnClickListener {view->
            checkScheduleAirport("DEPARTURE" , DEPARTURE_AIRPORT_CODE)
        }

        img_arrival.setOnClickListener {view->
            checkScheduleAirport("ARRIVAL" , ARRIVAL_AIRPORT_CODE)
        }

        select_Date.setOnClickListener {
            selectedDate(select_Date)
        }


    }

    private fun initToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar!!.title = "Schedule airport"
        Helper.setSystemBarColor(this@MainActivity)
    }

    //region Select Date
    private fun selectedDate(select_Date: TextView_Roboto_Medium?) {
        val cur_calender = Calendar.getInstance()
        val datePicker = DatePickerDialog.newInstance(
            { view, year, monthOfYear, dayOfMonth ->
                val calendar = Calendar.getInstance()
                calendar.set(Calendar.YEAR, year)
                calendar.set(Calendar.MONTH, monthOfYear)
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                val date_ship_millis = calendar.timeInMillis
                select_Date!!.text = Helper.scheduleDate(date_ship_millis)
                dateSelected = Helper.scheduleDate(date_ship_millis)

            },
            cur_calender.get(Calendar.YEAR),
            cur_calender.get(Calendar.MONTH),
            cur_calender.get(Calendar.DAY_OF_MONTH)
        )

        //set dark light
        datePicker.isThemeDark = false
        datePicker.accentColor = resources.getColor(R.color.colorPrimary)
        datePicker.minDate = cur_calender
        datePicker.show(fragmentManager, "Datepickerdialog")
    }
    //endregion

    private fun initViewModel(departurE_AIRPORT_CODE: Int, arrivaL_AIRPORT_CODE: Int) {

        val airportsObserver = Observer<String> { notView ->
            println("============What i Retrieve is $notView")
            println("============What i Retrieve is $notView")

        }

        mViewModel = ViewModelProviders.of(this).get(ShareFlightViewModel::class.java)
        mViewModel!!.departureLiveData.observe(this, airportsObserver)


    }


    //region Check airport
    private fun checkScheduleAirport(arrive: String, position: Int) {
        println("is------ Result is now  and $position")
        val intent =(Intent(this, ScheduleActivity::class.java))
        startActivityForResult(intent, position)

    }
    //endregion

    //TODO check time out timeout

    private fun handleResponse(scheduleList: List<Schedule>) {
        Helper.log("scheduleList Response $scheduleList")
        progress_bar.visibility = View.GONE
        lyt_no_connection.visibility = View.GONE
        adapter = ScheduleAdapter(scheduleList, this@MainActivity)
        recycler_schedule.adapter = adapter


    }

    fun collectAirportCode(departure: String, arrival:String){
        progress_bar.visibility = View.VISIBLE
        if (Apps.aiport.departureAirport !=arrival){
            //Display the departureLiveData details
            if (Apps.aiport.departureAirport.isNotEmpty() && arrival.isNotEmpty()){
                println("Not Empty $dateSelected")
                println("Holder wwww  ${Apps.aiport.departureAirport} wwwwww kakak0002 $arrival")
                if (dateSelected != null){
                    println("dateSelected $dateSelected")
                    //fetchFlight(Apps.aiport.departureAirport, arrival,dateSelected)
                    //realSingleExample(Apps.aiport.departureAirport, arrival,dateSelected)
                    progress_bar.visibility = View.VISIBLE
                    GlobalScope.launch {
                        fetchAllFlight(Apps.aiport.departureAirport, arrival,dateSelected!!)
                    }
                }else{
                    Toast.makeText(this, "Please Select Travel Date ", Toast.LENGTH_LONG).show()
                }




            }else{
                progress_bar.visibility = View.VISIBLE
                println("Is Empty Man ")

            }

        }else{
            //display an error
            println("display an errok")
        }
    }

    @SuppressLint("CheckResult")
    private fun fetchAllFlight(departureAirport: String, arrival: String, dateSelected: String) {
        presenter.getflight(departureAirport, arrival, dateSelected)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                flight->


                println("Response an😬 errok $flight")
                handleResponse(flight!!.ScheduleResource.Schedule)
            },{
                flightError->
                println("Error an👹 errok ${flightError.message}")
                progress_bar.visibility = View.GONE




            }).disposedBy(composit)
    }




    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        Helper.log("Start Calling on Result------------$data")
        lyt_no_connection.visibility = View.GONE

        var departureCode = ""
        var arrivalCode = ""


        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK) {
            val airport = deserializeInput(data)
            println("onActivityResult ====== $airport")
            // btn1 = true
            if (airport != null) {
//                val selectAirportOrigin = scheduleAirport.airporCode
//                // departureAirportCode = selectAirportOrigin
//                println("onActivityResult$selectAirportOrigin ")
                //  initViewModel(DEPARTURE_AIRPORT_CODE,ARRIVAL_AIRPORT_CODE)
                if (requestCode == DEPARTURE_AIRPORT_CODE) {
                    println("DEPARTURE_AIRPORT_NAME ------${airport.name.name.countryName} ")
                    txt_departure.text = airport.name.name.countryName
                    txt_departure_code.text = airport.airporCode

                    departureCode = airport.airporCode
//                        mViewModel!!.setFlight(departureCode)

                    Apps.aiport.departureAirport = departureCode
                    //save I
                    collectAirportCode(departureCode, arrivalCode)


                } else if (requestCode == ARRIVAL_AIRPORT_CODE) {
                    println("ELSE ARRIVAL CODE ------ ${airport.name.name.countryName}")
                    txt_arrival.text = airport.name.name.countryName
                    txt_destination_code.text = airport.airporCode
                    arrivalCode = airport.airporCode
                    // mViewModel!!.setarrivalAirpot(arrivalCode)
                    collectAirportCode(departureCode, arrivalCode)



                }
//
            }


        }

    }



    fun deserializeInput(data: Intent?): Airport? {
        return  data!!.getSerializableExtra(AIRPORT_ORIGIN_DEPARTURE) as? Airport
    }

    fun gotoAirportSchedule(){
        val intent =(Intent(this, ScheduleActivity::class.java))
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)

    }




}
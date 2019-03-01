package com.interview.safeboda.activities.schedules

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import com.interview.safeboda.activities.map.MapsActivity
import com.interview.safeboda.R
import com.interview.safeboda.modelLayer.model.schedule.Schedule
import com.interview.safeboda.utils.helper.Helper


class ScheduleAdapter(var scheduleList: List<Schedule>, var context: Context) :
    androidx.recyclerview.widget.RecyclerView.Adapter<ScheduleAdapter.ViewHolder>() {

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {

        val view = LayoutInflater.from(p0.context).inflate(R.layout.item_schedule, p0, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return scheduleList.count()
    }

    override fun onBindViewHolder(holder: ViewHolder, p1: Int) {
        holder.bindViews(scheduleList[p1], context)
    }


    inner class ViewHolder(itemView: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView) {
//        val date = itemView.findViewById<TextView>(R.id.txtDate)
        val totalDuration = itemView.findViewById<TextView>(R.id.txt_totalDuration)
        val departure = itemView.findViewById<TextView>(R.id.txtDeparture)
        val arival = itemView.findViewById<TextView>(R.id.arrival)
        val arrivaTime = itemView.findViewById<TextView>(R.id.arrival_time)

        val departureTime = itemView.findViewById<TextView>(R.id.departure_time)
        val flightDetails = itemView.findViewById<TextView>(R.id.flight_datails)
        val gotoMap = itemView.findViewById<LinearLayout>(R.id.goto_map)


        fun bindViews(schedule: Schedule, context: Context) {
            totalDuration.text = schedule.total.Duration
    schedule.flight.forEachIndexed { index, responseFlight ->
                departure.text = responseFlight.Departure.AirportCode
                departureTime.text = Helper.dateConversion(responseFlight.Departure.ScheduledTimeLocal.DateTime)
                arival.text = responseFlight.Arrival.AirportCode
                arrivaTime.text = Helper.dateConversion(responseFlight.Arrival.ScheduledTimeLocal.DateTime)
                flightDetails.text = responseFlight.MarketingCarrier.FlightNumber.toString()

            }

         //   scheduleList.forEachIndexed { index, responseFlight ->

               // val gson  = Gson()
             //   val json = gson.toJson(schedule.flight)
//                val response = Helper.gson.fromJson(json, Flight::class.java)
//
//                Helper.log(response.toString())
//
//                departure.text =     response.Departure.AirportCode
//                departureTime.text = Helper.dateConversion(response.Departure.ScheduledTimeLocal.DateTime)
//                arival.text = response.Arrival.AirportCode
//                arrivaTime.text = Helper.dateConversion(response.Arrival.ScheduledTimeLocal.DateTime)
//                flightDetails.text = response.MarketingCarrier.FlightNumber.toString()

          //  }



            gotoMap.setOnClickListener {
                context.startActivity(Intent(context, MapsActivity::class.java))
            }
        }

    }

    //2019-02-27T06:55


   // MMMMM dd,yyyy
}
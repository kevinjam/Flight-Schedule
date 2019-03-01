package com.interview.safeboda.activities.airport.recycler

import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.interview.safeboda.R
import com.interview.safeboda.modelLayer.model.airport.Airport

class AirportViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    
    val context = itemView.context
    val nameAirport: TextView
    val countryCode: TextView
    val cityCode: TextView
    val timeZone: TextView
    val openSelected: LinearLayout

    init {
        nameAirport = itemView.findViewById(R.id.txtairport_name)
        countryCode = itemView.findViewById(R.id.txt_country_code)
        cityCode = itemView.findViewById(R.id.txt_city_code)
        timeZone = itemView.findViewById(R.id.txt_time_zone)
        openSelected = itemView.findViewById(R.id.main_layout)


        // nameAirport.text = "${scheduleAirport.Names.name.countryName} ${scheduleAirport.city}"
        // countryCode.text = scheduleAirport.country
        // timeZone.text = scheduleAirport.timezone.replace("/", " ")
        
        
    }

    fun configureWith(schedule: Airport) {
        nameAirport.text ="${ schedule.name.name.countryName} ${schedule.city}"
        countryCode.text = schedule.country
        timeZone.text = schedule.timezone.replace("/", " ")
    }
}
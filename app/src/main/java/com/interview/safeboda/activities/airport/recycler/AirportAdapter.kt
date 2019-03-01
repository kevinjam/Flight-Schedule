package com.interview.safeboda.activities.airport.recycler

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import com.interview.safeboda.R
import com.interview.safeboda.modelLayer.model.airport.Airport
import com.interview.safeboda.common.AirportCallback
import io.reactivex.disposables.CompositeDisposable


class AirportAdapter(
    val scheduleAirport: ArrayList<Airport>,
    val context: Context
) : androidx.recyclerview.widget.RecyclerView.Adapter<androidx.recyclerview.widget.RecyclerView.ViewHolder>() {
    private val comp = CompositeDisposable()



    private val VIEW_ITEM = 1
    private val VIEW_SECTION = 0

    var selectedPosition = -1

    private var mOnItemClickListener: OnItemClickListener? = null


    private var mAirportCallback: AirportCallback? = null


    init {
        this.mAirportCallback = context as AirportCallback
    }


    interface OnItemClickListener {
        fun onItemClick(view: View, obj: Airport, position: Int)
    }

    fun setOnItemClickListener(mItemClickListener: OnItemClickListener) {
        this.mOnItemClickListener = mItemClickListener
    }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): androidx.recyclerview.widget.RecyclerView.ViewHolder {
        // val view = LayoutInflater.from(parent.context).inflate(R.layout.item_airport, parent, false)

        //return ViewHolder(view)

        val vh: androidx.recyclerview.widget.RecyclerView.ViewHolder
        if (viewType == VIEW_ITEM) {
            val v = LayoutInflater.from(parent.context).inflate(R.layout.item_airport, parent, false)
            vh = OriginalViewHolder(v)
        } else {
            val v = LayoutInflater.from(parent.context).inflate(R.layout.item_progress, parent, false)
            vh = SectionViewHolder(v)
        }

        return vh
    }

    override fun getItemCount(): Int {
        return scheduleAirport.count()
    }


    override fun getItemViewType(position: Int): Int {

        if (this.scheduleAirport[position].section) {

            return VIEW_SECTION
        } else {

            return VIEW_ITEM

        }


        // return if (this.airportList[position].section) VIEW_SECTION else VIEW_ITEM
    }


    override fun onBindViewHolder(holder: androidx.recyclerview.widget.RecyclerView.ViewHolder, position: Int) {

        val p = scheduleAirport.get(position)
        if (holder is OriginalViewHolder) {
            val view = holder
            holder.bindViews(scheduleAirport[position], context, position)

        } else {
            val view = holder as SectionViewHolder

        }
//
    }

    inner class OriginalViewHolder(itemView: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView) {

        val nameAirport = itemView.findViewById<TextView>(R.id.txtairport_name)
        val countryCode = itemView.findViewById<TextView>(R.id.txt_country_code)
        val cityCode = itemView.findViewById<TextView>(R.id.txt_city_code)
        val timeZone = itemView.findViewById<TextView>(R.id.txt_time_zone)
        val openSelected = itemView.findViewById<LinearLayout>(R.id.main_layout)

        fun bindViews(
            airport: Airport,
            context: Context,
            position: Int
        ) {

            println("Response 👺 $airport")

            nameAirport.text = "${airport.name.name.countryName} ${airport.city}"
            countryCode.text = airport.country
            timeZone.text = airport.timezone.replace("/", " ")

              println("DSDDSDSDDD ${airport.position.coordinates.latitude}")

            openSelected.isSelected = selectedPosition == position


            openSelected.setOnClickListener { view ->
                mAirportCallback!!.onMethodCallback(view, airport)

                if (mOnItemClickListener != null) {
                    mOnItemClickListener!!.onItemClick(view, scheduleAirport[position], position)
                }
                selectedPosition = position
                notifyDataSetChanged()


//                val intent = Intent(context, Class<ScheduleActivity::class.java>)
//                intent.putExtra(AIRPORT_ORIGIN_DEPARTURE, scheduleAirport)
//                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
//                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
//                itemView.setOnClickListener { listClicked.onClick() }
//                listClicked.onClick(view, adapterPosition)
//                context.startActivity(intent)


            }

        }


    }


    class SectionViewHolder(v: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(v) {


        // var title_section: TextView

//        init {
//            title_section = v.findViewById<View>(R.id.title_section) as TextView
//        }


    }


    fun insertItem(index: Int, airport: Airport) {
        scheduleAirport.add(index, airport)
        notifyItemInserted(index)
    }

}
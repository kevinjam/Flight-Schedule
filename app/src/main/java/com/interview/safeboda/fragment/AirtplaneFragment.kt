package com.interview.safeboda.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import com.interview.safeboda.R
import com.interview.safeboda.modelLayer.model.airport.Airport
import com.interview.safeboda.modelLayer.model.airport.Payload
import com.interview.safeboda.network.endpoint.Lufthansa
import com.interview.safeboda.utils.helper.Helper
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import android.widget.LinearLayout.VERTICAL
import android.widget.TextView
import com.interview.safeboda.activities.airport.recycler.AirportAdapter


class AirtplaneFragment : androidx.fragment.app.DialogFragment()  {
    var composit: CompositeDisposable? = null
    val apiService : Lufthansa

    lateinit var progress:ProgressBar
    lateinit var airpline:List<Airport>
    lateinit var no_network_message:TextView
    lateinit var rootlayout: androidx.coordinatorlayout.widget.CoordinatorLayout

    init {
        apiService = Helper.createService(Lufthansa::class.java)

    }



    lateinit var rv: androidx.recyclerview.widget.RecyclerView
    lateinit var adapter: AirportAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.airlinelayout, container)

        composit = CompositeDisposable()
        requestAirport()

initialiseVies(view)


        return view
    }

    private fun initialiseVies(view: View) {

        //recy
        rv = view.findViewById<View>(R.id.airplaine_recycler) as androidx.recyclerview.widget.RecyclerView
        no_network_message = view.findViewById<View>(R.id.no_network_message) as TextView
        rv.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(this.activity)
        rv.itemAnimator = androidx.recyclerview.widget.DefaultItemAnimator()
        val decoration = androidx.recyclerview.widget.DividerItemDecoration(context!!.applicationContext, VERTICAL)
        rv.addItemDecoration(decoration)

        progress = view.findViewById(R.id.progress_circular)

        rv.visibility = View.GONE
        progress.visibility = View.VISIBLE

        this.dialog.setTitle("Airplain")


//        rootlayout = view.findViewById(R.id.coordinatorLayout)
//
//        val snackbar = Snackbar.make(rootlayout, "Snackbar test", Snackbar.LENGTH_SHORT)
//        val sbView = snackbar.view
//        val textView = sbView.findViewById(android.support.design.R.id.snackbar_text) as TextView
//        textView.setTextColor(Color.YELLOW)
//        snackbar.show()


    }


    fun airport(): Observable<Payload> {
        return apiService.airports()

    }


    private fun requestAirport() {
        Helper.log("Start Calling Request ---")

        composit!!.add(airport()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({
                    response->
                handlerResponse(response)


            },{
                    errors->
                Helper.log("Error --- ${errors.message}")
                Helper.log("Error --- ${errors.stackTrace}")
                no_network_message.visibility=View.VISIBLE
                no_network_message.text = errors.message
                rv.visibility = View.GONE
                progress.visibility = View.GONE

                


            }))




    }

    private fun handlerResponse(response: Payload?) {
        airpline = response!!.airportResource.airport.airport
        Helper.log("Response --- $response")
        val list = airpline as ArrayList<Airport>
        rv.visibility = View.VISIBLE
        progress.visibility = View.GONE
//        adapter = AirportAdapter(list, activity!!, rv, )
        rv.adapter = adapter

//        adapter.setOnItemListener(object :AirportAdapter.OnItemClickListener{
//            override fun onItemClick(item: ArrayList<airport>) {
//                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//                Helper.log("Clicked00000 --- $response")
//
//            }
//
//        })

//load more listener
//        adapter.setOnLoadMoreListener(object :AirportAdapter.OnLoadMoreListener{
//            override fun onLoadMore(scheduleAirport: airport) {
//                if (list.count() <=10){
//                    list.add(scheduleAirport)
//                    adapter.notifyItemInserted(list.count() -1)
//                    Handler().postDelayed({
//                        list.removeAt(list.size - 1)
//                        adapter.notifyItemRemoved(list.size)
//
//                        //scheduleAirport
//                        list.add(scheduleAirport)
//                        adapter.notifyDataSetChanged()
//                        adapter.setLoaded()
//
//                    }, 1000)
//                }else{
//                    Toast.makeText(activity, "Loading data completed", Toast.LENGTH_SHORT).show()
//                }
//
//            }
//
//        })



    }


}

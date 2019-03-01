package com.interview.safeboda.modelLayer.model.schedule

import com.google.gson.Gson
import com.google.gson.JsonArray
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.google.gson.reflect.TypeToken

import java.util.ArrayList

class classMyApiResponse {

    @SerializedName("results")
    @Expose
    val results: JsonElement? = null // This has been Changed.

    private val resultsList: List<Flight>? = null // This has been added newly and cannot be initialized by gson.

    fun getResultsList(): List<Flight>? {
        var resultList: MutableList<Flight> = ArrayList() // Initializing here just to cover the null pointer exception
        val gson = Gson()
        if (results is JsonObject) {
            resultList.add(gson.fromJson(results, Flight::class.java))
        } else if (results is JsonArray) {
            val founderListType = object : TypeToken<ArrayList<Flight>>() {

            }.type
            resultList = gson.fromJson<List<Flight>>(results, founderListType) as MutableList<Flight>
        }
        return resultList // This is the actual list which i need and will work well with my code.
    }
}

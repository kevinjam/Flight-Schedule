package com.interview.safeboda.modelLayer.model.airport

import com.google.gson.annotations.SerializedName
import com.interview.safeboda.utils.helper.Helper
import java.io.Serializable

data class Payload(
    @SerializedName("AirportResource")
    val airportResource: AirportResource
) {
    override fun toString(): String {
        return Helper.toStringGson().toJson(this)
    }
}

data class AirportResource(
    @SerializedName("Airports")
    val airport:Airports,
    @SerializedName("Meta")
    val meta: Meta
)

data class Airport(
    @SerializedName("AirportCode")
    val airporCode: String,
    @SerializedName("CityCode")
    val city: String,
    @SerializedName("CountryCode")
    val country: String,
    @SerializedName("LocationType")
    val location: String,
    @SerializedName("Names")
    val name: Names,
    @SerializedName("Position")
    val position:Position,
    @SerializedName("TimeZoneId")
    val timezone: String,
    @SerializedName("UtcOffset")
    val offset: Double,
    val section: Boolean = false

):Serializable {
    override fun toString(): String {
        return Helper.toStringGson().toJson(this)
    }
}

data class Airports(
    @SerializedName("Airport")
    val airport: List<Airport>
):Serializable

data class Coordinate(
    val latitude: Double,
    val longitude: Double
):Serializable

data class Link(
    var model: InfoModel
)

data class InfoModel(
    @SerializedName("@Href")
    var link: String,
    @SerializedName("@Rel")
    val rel: String
)

data class Meta(
    val Version: String,
    val Link: List<Link>,
    val TotalCount: Int
)

data class Name(
    @SerializedName("@LanguageCode")
    val languageCode: String,
    @SerializedName("$")
    val countryName:String
):Serializable

data class Names(
    @SerializedName("Name")
    val name: Name
):Serializable

data class Position(
    @SerializedName("Coordinate")
    val coordinates: Coordinate
):Serializable


data class RequestToken(
                        var access_token:String,
                        var token_type:String,
                        var expires_in:Int,
                        var error:String){

    override fun toString(): String {
        return Helper.toStringGson().toJson(this)
    }
}


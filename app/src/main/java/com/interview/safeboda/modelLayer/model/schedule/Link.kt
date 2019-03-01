package com.interview.safeboda.modelLayer.model.schedule

import com.google.gson.annotations.SerializedName

data class Link(

    @SerializedName("@Href") val href: String = "",
    @SerializedName("@Rel") val rel: String = ""
)
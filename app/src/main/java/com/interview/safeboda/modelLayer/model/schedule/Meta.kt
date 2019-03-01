package com.interview.safeboda.modelLayer.model.schedule

import com.google.gson.annotations.SerializedName

data class Meta(
    @SerializedName("@Version") val version: String = "",
    val Link: List<Link>
)
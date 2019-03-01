package com.interview.safeboda.activities.splash

import com.interview.safeboda.modelLayer.model.airport.RequestToken
import com.interview.safeboda.modelLayer.ModelLayer
import io.reactivex.Single


class SplashPresenter{

    private val modelLayer = ModelLayer.shared //normally injected


    //region GET_TOKEN
    fun getToken(): Single<RequestToken> {
        return modelLayer.getTokenRx()
    }
    //endregion



}
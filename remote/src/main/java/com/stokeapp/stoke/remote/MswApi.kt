package com.stokeapp.stoke.remote

import com.stokeapp.stoke.remote.model.SurfReportResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface MswApi {
    @GET("forecast/")
    fun getSurfReport(
        @Query("spot_id") spotId: String
    ): Single<Array<SurfReportResponse>>
}
package com.stokeapp.stoke.domain.repository

import com.stokeapp.stoke.domain.model.SurfReportModel
import io.reactivex.Single

interface SurfReportRepository {
    fun getSurfReport(spotId: String): Single<SurfReportModel>
}
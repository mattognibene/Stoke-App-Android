package com.stokeapp.stoke.dashboard.surf

import com.stokeapp.stoke.domain.model.SurfReportModel

sealed class SurfState {
    data class Success(val report: SurfReportModel) : SurfState()
    data class Failure(val error: Throwable) : SurfState()
}
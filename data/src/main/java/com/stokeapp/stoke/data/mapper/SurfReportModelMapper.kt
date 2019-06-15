package com.stokeapp.stoke.data.mapper

import com.stokeapp.stoke.domain.common.Mapper
import com.stokeapp.stoke.domain.model.SurfReportModel
import com.stokeapp.stoke.remote.model.SurfReportResponse
import javax.inject.Inject

class SurfReportModelMapper @Inject constructor() : Mapper<SurfReportResponse, SurfReportModel> {
    override fun map(t: SurfReportResponse): SurfReportModel {
        return SurfReportModel(
                timestamp = t.timestamp,
                localTimeStamp = t.localTimeStamp,
                fadedRating = t.fadedRating,
                solidRating = t.solidRating,
                minBreakingHeight = t.swell.minBreakingHeight,
                absMinBreakingHeight = t.swell.absMinBreakingHeight,
                maxBreakingHeight = t.swell.maxBreakingHeight,
                absMaxBreakingHeight = t.swell.absMaxBreakingHeight,
                unit = t.swell.unit,
                height = t.swell.components.primary.height,
                period = t.swell.components.primary.period,
                direction = t.swell.components.primary.direction,
                compassDirection = t.swell.components.primary.compassDirection
        )
    }
}
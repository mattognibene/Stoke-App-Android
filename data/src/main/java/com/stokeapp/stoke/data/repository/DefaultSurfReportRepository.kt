package com.stokeapp.stoke.data.repository

import com.stokeapp.stoke.data.mapper.SurfReportModelMapper
import com.stokeapp.stoke.domain.model.SurfReportModel
import com.stokeapp.stoke.domain.repository.SurfReportRepository
import com.stokeapp.stoke.remote.MswApi
import io.reactivex.Single
import javax.inject.Inject

class DefaultSurfReportRepository @Inject constructor(
    private val api: MswApi,
    private val mapper: SurfReportModelMapper
) : SurfReportRepository {
    override fun getSurfReport(spotId: String): Single<SurfReportModel> {
        return api.getSurfReport(spotId)
                .map { array ->
                    mapper.map(array[0])
                }
    }
}
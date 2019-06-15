package com.stokeapp.stoke.domain.interactor.surf

import com.stokeapp.stoke.domain.interactor.SingleUseCase
import com.stokeapp.stoke.domain.model.SurfReportModel
import com.stokeapp.stoke.domain.repository.SurfReportRepository
import io.reactivex.Single
import javax.inject.Inject

class GetSurfReport @Inject constructor(
    private val surfReportRepository: SurfReportRepository
) : SingleUseCase<GetSurfReport.Params, SurfReportModel>() {
    override fun execute(params: Params): Single<SurfReportModel> {
        return surfReportRepository.getSurfReport(params.spotId)
    }

    data class Params(val spotId: String)
}
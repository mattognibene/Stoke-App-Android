package com.stokeapp.stoke.remote.model

data class SurfReportResponse(
    val timestamp: Long,
    val localTimeStamp: Long,
    val fadedRating: Int,
    val solidRating: Int,
    val swell: Swell
) {
    data class Swell(
        val absMinBreakingHeight: Float,
        val absMaxBreakingHeight: Float,
        val unit: String,
        val minBreakingHeight: Float,
        val maxBreakingHeight: Float,
        val components: Components
    )

    data class Components(
        val combined: Report,
        val primary: Report,
        val secondary: Report
    )

    data class Report(
        val height: Float,
        val period: Float,
        val direction: Float,
        val compassDirection: String
    )
}
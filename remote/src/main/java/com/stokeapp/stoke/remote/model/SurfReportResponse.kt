package com.stokeapp.stoke.remote.model

data class SurfReportResponse(
    val timestamp: Long,
    val localTimeStamp: Long,
    val fadedRating: Int,
    val solidRating: Int,
    val swell: Swell
) {
    data class Swell(
        val minBreakingHeight: Float,
        val absMinBreakingHeight: Float,
        val maxBreakingHeight: Float,
        val absMaxBreakingHeight: Float,
        val unit: String,
        val primary: Primary
    )

    data class Primary(
        val height: Float,
        val period: Float,
        val direction: Float,
        val compassDirection: String
    )
}
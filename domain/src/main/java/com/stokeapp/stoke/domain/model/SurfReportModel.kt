package com.stokeapp.stoke.domain.model

data class SurfReportModel(
    val timestamp: Long,
    val localTimeStamp: Long,
    val fadedRating: Int,
    val solidRating: Int,
    val minBreakingHeight: Float,
    val absMinBreakingHeight: Float,
    val maxBreakingHeight: Float,
    val absMaxBreakingHeight: Float,
    val unit: String,
    val height: Float,
    val period: Float,
    val direction: Float,
    val compassDirection: String
)
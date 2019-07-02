package com.stokeapp.stoke.dashboard

enum class Location(
    val mswSpotId: String,
    val openWeatherId: String,
    val location: String
) {
    ATLANTIC_CITY(
            location = "Atlantic City, NJ",
            mswSpotId = "390",
            openWeatherId = "4500546"
    )
}
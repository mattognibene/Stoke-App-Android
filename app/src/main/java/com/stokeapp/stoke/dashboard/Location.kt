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
    ),
    SAN_DIEGO(
        location = "San Diego, CA",
            mswSpotId = "297",
            openWeatherId = "5391811"
    );
    companion object {
        fun getLocation(name: String?): Location? {
            return when (name) {
                ATLANTIC_CITY.name -> ATLANTIC_CITY
                SAN_DIEGO.name -> SAN_DIEGO
                else -> null
            }
        }
    }
}
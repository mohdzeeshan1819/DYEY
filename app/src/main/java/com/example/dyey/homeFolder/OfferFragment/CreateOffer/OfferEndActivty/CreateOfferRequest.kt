package com.example.dyey.homeFolder.OfferFragment.CreateOffer.OfferEndActivty

data class CreateOfferRequest(
    val restaurant_name: String,
    val restaurant_lat: Double,
    val restaurant_long: Double,
    val restaurant_image: String,
    val restaurant_address: String,
    val meal_type: String,
    val spending_amount: String,
    val cuisines: String,
    val date: String,
    val time: String,
    val day: String,
    val attire: String,
    val intention: String,
    val restaurant_ratings: Int,
    val restaurant_status: String
)

data class CreateOfferResponse(
    val status: Boolean,
    val message: String
)

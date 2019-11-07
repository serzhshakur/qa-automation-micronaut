package dev.serzhshakur.api.beans

import com.fasterxml.jackson.annotation.JsonProperty

data class WeatherResponse(
    val weather: List<Weather>,
    val wind: Wind,
    val main: Main,
    val sys: Sys,
    @field:JsonProperty("coord") val coordinates: Coordinates,
    @field:JsonProperty("name") val cityName: String,
    @field:JsonProperty("id") val cityId: String
)

data class Main(
    val temp: Double
)

data class Coordinates(
    val lon: Double,
    val lat: Double
)

data class Weather(
    val main: String,
    val description: String
)

data class Wind(
    val speed: Double
)

data class Sys(
    @field:JsonProperty("country") val countryCode: String
)
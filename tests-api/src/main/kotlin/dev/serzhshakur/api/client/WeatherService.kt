package dev.serzhshakur.api.client

import dev.serzhshakur.api.beans.WeatherResponse
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.QueryValue
import io.micronaut.http.client.annotation.Client

@Client(
    value = "\${api.openweather.url}",
    path = "\${api.openweather.path}"
)
interface WeatherService {

    @Get
    fun getByCityName(
        @QueryValue("q") queryString: String = "London"
    ): WeatherResponse

    @Get
    fun getByCoordinates(
        @QueryValue("lat") latitude: Double,
        @QueryValue("lon") longitude: Double
    ): WeatherResponse

    @Get
    fun getByCityId(
        @QueryValue("id") cityId: String
    ): WeatherResponse

    @Get
    fun getByZipCode(
        @QueryValue("zip") zip: List<String>
    ): WeatherResponse
}

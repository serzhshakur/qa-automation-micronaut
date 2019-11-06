package dev.serzhshakur.api.client

import dev.serzhshakur.api.beans.WeatherResponse
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.QueryValue
import io.micronaut.http.client.annotation.Client

@Client(
    value = "\${api.owm.url}",
    path = "\${api.owm.path}"
)
interface WeatherService {

    @Get
    fun getByCityName(
        @QueryValue("q") queryString: String = "London"
    ): WeatherResponse

    @Get
    fun getByCoordinates(
        @QueryValue("lat") latitude: String,
        @QueryValue("lon") longitude: String
    ): WeatherResponse

    @Get
    fun getByCityId(
        @QueryValue("id") cityId: String
    ): WeatherResponse
}

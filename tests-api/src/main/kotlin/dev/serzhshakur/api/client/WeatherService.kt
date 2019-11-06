package dev.serzhshakur.api.client

import dev.serzhshakur.api.beans.WeatherResponse
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.QueryValue
import io.micronaut.http.client.annotation.Client

@Client(value = "\${api.owm.url}")
interface WeatherService {

    @Get("/weather?APPID=\${api.owm.key}")
    fun retrieveWeather(
        @QueryValue("units") temperatureUnits: String = "metric",
        @QueryValue("q") queryString: String = "London"
    ): WeatherResponse
}

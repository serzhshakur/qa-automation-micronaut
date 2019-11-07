package dev.serzhshakur

import dev.serzhshakur.api.client.WeatherService
import io.micronaut.test.annotation.MicronautTest
import javax.inject.Inject
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

@MicronautTest
class Test {

    @Inject
    private lateinit var weatherService: WeatherService

    @Test
    fun `weather data can be found by city name`() {
        val city = "Riga"
        weatherService.getByCityName(queryString = city).apply {
            assertThat(cityName).isEqualTo(city)
            assertThat(sys.countryCode).isEqualTo("LV")
            assertThat(coordinates.lat).isEqualTo(56.95)
            assertThat(coordinates.lon).isEqualTo(24.11)
            assertThat(wind.speed).isGreaterThanOrEqualTo(0.0)
            assertThat(main.temp).isBetween(-35.0, 35.0)
        }
    }

    @Test
    fun `weather data can be found by coordinates`() {
        val lat = 56.95
        val lon = 24.11
        val result = weatherService.getByCoordinates(
            latitude = lat,
            longitude = lon
        )
        result.apply {
            assertThat(cityName).isEqualTo("Vecrīga")
            assertThat(sys.countryCode).isEqualTo("LV")
            assertThat(coordinates.lat).isEqualTo(lat)
            assertThat(coordinates.lon).isEqualTo(lon)
        }
    }

    @Test
    fun `weather data can be found by city id`() {
        val cityId = "456172"
        weatherService.getByCityId(cityId).apply {
            assertThat(this.cityId).isEqualTo(cityId)
            assertThat(cityName).isEqualTo("Riga")
            assertThat(sys.countryCode).isEqualTo("LV")
        }
    }

    @Test
    fun `weather data can be found by zip code`() {
        val zipWithCountry = listOf("50001", "es")
        weatherService.getByZipCode(zipWithCountry).apply {
            assertThat(cityName).isEqualTo("Zaragoza")
            assertThat(sys.countryCode).isEqualTo("ES")
        }
    }

    @Test
    fun `no results are returned if non existent city is entered`() {
        val result = weatherService.getByCityName(queryString = "Abcdef")
        assertThat(result).isNull()
    }
}

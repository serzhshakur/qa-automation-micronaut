package dev.serzhshakur

import dev.serzhshakur.api.client.WeatherService
import io.micronaut.test.annotation.MicronautTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import javax.inject.Inject

@MicronautTest
class Test {

    @Inject
    private lateinit var weatherService: WeatherService

    @Test
    fun `city can be found by exact name`() {
        val city = "Riga"
        weatherService.retrieveWeather(queryString = city).apply {
            assertThat(cityName).isEqualTo(city)
            assertThat(sys.countryCode).isEqualTo("LV")
            assertThat(coordinates.lat).isEqualTo(56.95)
            assertThat(coordinates.lon).isEqualTo(24.11)
            assertThat(wind.speed).isGreaterThan(0.0)
            assertThat(main.temp).isBetween(-35.0, 35.0)
        }
    }

    @Test
    fun `no results are returned if non existent city is entered`() {
        val result = weatherService.retrieveWeather(queryString = "Abcdef")
        assertThat(result).isNull()
    }
}

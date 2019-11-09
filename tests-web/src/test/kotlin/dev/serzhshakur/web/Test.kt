package dev.serzhshakur.web

import dev.serzhshakur.web.page.WiktionaryPage
import io.micronaut.test.annotation.MicronautTest
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream
import javax.inject.Inject

@MicronautTest
@TestInstance(PER_CLASS)
@DisplayName("Wiktionary search")
class Test {

    @Inject
    private lateinit var wiktionaryPage: WiktionaryPage

    @ParameterizedTest(name = "searching for {0}")
    @MethodSource("testData")
    fun `searching for various terms`(scenario: Scenario) {
        wiktionaryPage.apply {
            open()
            search(scenario.searchTerm)
            checkResultsHaveText(scenario.expectedResult)
        }
    }

    @Suppress("unused")
    fun testData(): Stream<Arguments> = Stream.of(
        Arguments.of(
            Scenario(
                searchTerm = "apple",
                expectedResult = "A common, round fruit produced by the tree Malus domestica, cultivated in temperate climates."
            )
        ),
        Arguments.of(
            Scenario(
                searchTerm = "pear",
                expectedResult = "An edible fruit produced by the pear tree, similar to an apple but elongated towards the stem."
            )
        )
    )

    data class Scenario(
        val searchTerm: String,
        val expectedResult: String
    )
}

package dev.serzhshakur.web

import dev.serzhshakur.WebTestsExtension
import dev.serzhshakur.web.page.WiktionaryPage
import io.micronaut.test.annotation.MicronautTest
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ArgumentsSource
import javax.inject.Inject

@MicronautTest
@TestInstance(PER_CLASS)
@DisplayName("Wiktionary search")
@ExtendWith(WebTestsExtension::class)
class Test {

    @Inject
    private lateinit var wiktionaryPage: WiktionaryPage

    @ParameterizedTest(name = "searching for {0}")
    @ArgumentsSource(ScenariosProvider::class)
    fun `searching for various terms`(scenario: Scenario) {
        wiktionaryPage.apply {
            open()
            search(scenario.searchTerm)
            checkResultsHaveText(scenario.expectedResult)
        }
    }
}

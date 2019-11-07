package dev.serzhshakur.web

import dev.serzhshakur.web.page.Page
import io.micronaut.test.annotation.MicronautTest
import org.junit.jupiter.api.Test
import javax.inject.Inject

@MicronautTest
class Test {

    @Inject
    private lateinit var page: Page

    @Test
    fun test() {
        val expectedDefinition =
            "A common, round fruit produced by the tree Malus domestica, cultivated in temperate climates."
        page.apply {
            open()
            search("apple")
            checkResultsHaveText(expectedDefinition)
        }
    }
}

package dev.serzhshakur.web

import org.junit.jupiter.api.extension.ExtensionContext
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.ArgumentsProvider
import java.util.stream.Stream

class ScenariosProvider : ArgumentsProvider {
    override fun provideArguments(context: ExtensionContext): Stream<out Arguments> =
        Stream.of(
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
}

data class Scenario(
    val searchTerm: String,
    val expectedResult: String
)

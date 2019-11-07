package dev.serzhshakur.web

import com.codeborne.selenide.Selenide
import io.micronaut.test.annotation.MicronautTest
import org.junit.jupiter.api.Test

@MicronautTest
class Test {

    @Test
    fun test() {
        Selenide.open("https://en.wiktionary.org/")
    }
}
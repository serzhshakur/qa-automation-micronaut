package dev.serzhshakur.web.page

import com.codeborne.selenide.Condition.visible
import com.codeborne.selenide.ElementsCollection
import com.codeborne.selenide.Selectors.byXpath
import com.codeborne.selenide.Selenide
import com.codeborne.selenide.Selenide.`$$`
import com.codeborne.selenide.Selenide.`$`
import com.codeborne.selenide.SelenideElement
import dev.serzhshakur.web.tools.anyHasText
import javax.inject.Singleton

@Singleton
class WiktionaryPage {

    private val searchInput: SelenideElement = `$`(".bodySearchWrap [name='search']")
    private val submitButton: SelenideElement = `$`(".bodySearchWrap [type='submit']")
    private val meanings: ElementsCollection = `$$`(byXpath("//*[@id='Noun']/ancestor::h3/following::ol[1]/li"))

    fun open() {
        Selenide.open("https://en.wiktionary.org/")
        searchInput.shouldBe(visible)
        submitButton.shouldBe(visible)
    }

    fun search(searchTerm: String) {
        searchInput.apply {
            clear()
            value = searchTerm
        }
        submitButton.click()
    }

    fun checkResultsHaveText(text: String) {
        meanings.shouldHave(anyHasText(text))
    }
}

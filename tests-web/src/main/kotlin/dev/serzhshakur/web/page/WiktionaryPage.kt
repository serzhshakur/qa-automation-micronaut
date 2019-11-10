package dev.serzhshakur.web.page

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

    fun open() {
        Selenide.open("https://en.wiktionary.org/")
    }

    fun searchBar(closure: SearchBar.() -> Unit) {
        SearchBar().closure()
    }

    fun results(closure: SearchResults.() -> Unit) {
        SearchResults().closure()
    }

    inner class SearchBar {
        private val searchInput: SelenideElement = `$`(".bodySearchWrap [name='search']")
        private val submitButton: SelenideElement = `$`(".bodySearchWrap [type='submit']")

        fun enterText(searchTerm: String) {
            searchInput.apply {
                clear()
                value = searchTerm
            }
        }

        fun submit() {
            submitButton.click()
        }
    }

    inner class SearchResults {
        private val results: ElementsCollection = `$$`(byXpath("//*[@id='Noun']/ancestor::h3/following::ol[1]/li"))

        fun anyContainsText(text: String) {
            results.shouldHave(anyHasText(text))
        }
    }
}

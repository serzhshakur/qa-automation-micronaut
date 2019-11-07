package dev.serzhshakur.web.tools

import com.codeborne.selenide.CollectionCondition
import com.codeborne.selenide.impl.WebElementsCollection
import org.openqa.selenium.WebElement

fun anyHasText(text: String): CollectionCondition {
    return object : CollectionCondition() {

        override fun apply(elements: List<WebElement>?): Boolean {
            return elements?.any { it.text.contains(text) } ?: false
        }

        override fun fail(
            collection: WebElementsCollection,
            elements: List<WebElement>,
            lastError: Exception,
            timeoutMs: Long
        ) {
            throw AssertionError(
                "None of the elements located by '${collection.description()}' contain text '$text",
                lastError
            )
        }

        override fun applyNull(): Boolean {
            return false
        }
    }
}

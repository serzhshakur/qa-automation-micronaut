package dev.serzhshakur

import com.codeborne.selenide.Configuration
import com.codeborne.selenide.WebDriverRunner
import com.codeborne.selenide.logevents.SelenideLogger
import io.qameta.allure.selenide.AllureSelenide
import org.junit.jupiter.api.extension.AfterEachCallback
import org.junit.jupiter.api.extension.BeforeAllCallback
import org.junit.jupiter.api.extension.ExtensionContext

class WebTestsExtension : BeforeAllCallback, AfterEachCallback {

    override fun beforeAll(context: ExtensionContext) {
        Configuration.browserSize = "1920x1080"
        Configuration.timeout = 20000

        val allureListener = AllureSelenide()
        SelenideLogger.addListener("Allure Selenide Listener", allureListener)
    }

    override fun afterEach(context: ExtensionContext) {
        WebDriverRunner.clearBrowserCache()
    }
}

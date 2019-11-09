## Build status [![Build Status](https://travis-ci.org/serzhshakur/qa-automation-micronaut.svg?branch=master)](https://travis-ci.org/serzhshakur/qa-automation-micronaut)

## Description
This is a tests automation project that consists of two modules
- automated tests written for [Open Weather API](http://openweathermap.org/current)
- a web tests designed to cover search functionality on [wiktionary.org](https://en.wiktionary.org/) website

#### Technology stack
- [Kotlin](https://kotlinlang.org) - a Next Gen JVM language that makes programming so pleasant process; 
- [Micronaut](https://micronaut.io) framework used in this project for the following reasons:
  - dependency injection;
  - application configuration properties;
  - http client;
- [Selenide](https://selenide.org) a handy wrapper around Selenium that brings nicer API and makes tests more reliable;
- [JUnit5](https://junit.org/junit5/docs/current/user-guide/) for running the tests;

#### Project structure

```$xslt
├── tests-api
│   └── src
│       ├── main
│       │   ├── ...
│       └── test
│           └── kotlin
│               ├── dev
│               │   └── serzhshakur
│               │       └── api
│               └── Test.kt
└── tests-web
    └── src
        ├── main
        │   └── ...
        └── test
            └── kotlin
                └── dev
                    └── serzhshakur
                        └── web
                            └── Test.kt

```
- API tests are located in `tests-api/src/test/kotlin/dev/serzhshakur/api/Test.kt`
- WEB tests are located in `tests-web/src/test/kotlin/dev/serzhshakur/web/Test.kt`

#### Operating systems' support
- Linux
- Mac OS 
- Windows (haven't tested)

#### Supported browsers
- Chrome
- Firefox

To set browser change `driver.name` property under _$projectDir/src/main/resources/test.properties_.
By default **Chrome** in headless mode is used.

## Tests execution

#### IntelliJ Idea

As Micronaut is used in this project you need to enable annotation processing in IntelliJ Idea (look [here](https://guides.micronaut.io/creating-your-first-micronaut-app/guide/) for more details): 
- Go to _File > Settings > Build, Execution, Deployment > Compiler > Annotation Processors_ 
- check "Enable annotation processing" 

In addition build and tests execution should be delegated to Gradle (look [here](https://www.jetbrains.com/help/idea/gradle.html#gradle_settings_access) how to do this).

To run a tests from IntelliJ IDEA 
- navigate to a `Test.kt` class  
- run a separate test or the whole suite. To do that just right click and choose `Run 'Test ...'`

##### APPID for Open Weather API
You need a valid APPID for Open Weather API. Check [here](https://openweathermap.org/appid) how to get one.
In order to globally set your APPID in IntelliJ do the following:
- Run > Edit Configurations > Templates > Gradle
- Add `-DapiToken={your_appid}` to `VM Options` 

#### Command line

To run test from command line use the following Gradle command

- API tests
```bash
./gradlew clean testApi -DapiToken=[your_appid}
```
- WEB tests
```bash
./gradlew clean testWeb -Dbrowser=chrome -Dheadless=true
```
`-Dheadless` allows to run web tests in headless mode
 
 `-Dbrowser` possible values `chrome` or `firefox`


For Windows machines you must run  `gradlew.bat` instead

### Travis CI
This project has integration with travis-ci so each time a new code is pushed to repo a pipeline on Travis is automatically triggered and the following tests are executed
- _Openweather API tests_
- _Wiktionary WEB tests using Chrome browser_
- _Wiktionary WEB tests using Firefox browser_

The current state of a build is [![Build Status](https://travis-ci.org/serzhshakur/qa-automation-micronaut.svg?branch=master)](https://travis-ci.org/serzhshakur/qa-automation-micronaut)
. You can also see the current status at a top of this readme. A stable Chrome version and latest Firefox version in a headless mode are used for running tests on CI.

package dev.serzhshakur.api.client

import io.micronaut.context.annotation.Value
import io.micronaut.http.HttpResponse
import io.micronaut.http.MutableHttpRequest
import io.micronaut.http.annotation.Filter
import io.micronaut.http.filter.ClientFilterChain
import io.micronaut.http.filter.HttpClientFilter
import io.micronaut.http.uri.UriBuilder
import org.reactivestreams.Publisher

@Filter("/data/**")
class AuthFilter(
    @Value("\${api.owm.key}") val key: String
) : HttpClientFilter {

    override fun doFilter(request: MutableHttpRequest<*>, chain: ClientFilterChain):
            Publisher<out HttpResponse<*>> {
        val uri = UriBuilder.of(request.uri)
            .queryParam("APPID", key)
            .queryParam("units", "metric")
            .build()
        return chain.proceed(request.uri(uri))
    }
}

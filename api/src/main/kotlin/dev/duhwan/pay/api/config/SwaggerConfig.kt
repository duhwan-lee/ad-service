package dev.duhwan.pay.api.config

import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.servers.Server
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class SwaggerConfig {

    @Bean
    fun devOpenApiCustom(): OpenAPI {
        val server = Server().url("/")
        return OpenAPI().addServersItem(server)
    }
}

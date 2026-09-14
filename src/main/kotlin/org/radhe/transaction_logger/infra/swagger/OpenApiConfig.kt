package org.radhe.transaction_logger.infra.swagger
import io.swagger.v3.oas.annotations.OpenAPIDefinition
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType
import io.swagger.v3.oas.annotations.info.Contact
import io.swagger.v3.oas.annotations.info.Info
import io.swagger.v3.oas.annotations.info.License
import io.swagger.v3.oas.annotations.security.SecurityScheme
import io.swagger.v3.oas.annotations.servers.Server
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
@OpenAPIDefinition(
    info = Info(
        title = "Transaction Logger API",
        version = "0.0.1",
        description = "REST API for managing accounts, monthly sessions, tags, and transaction logs.",
        contact = Contact(
            name = "Transaction Logger Support",
            email = "vikas.on@gmail.com"
        ),
        license = License(
            name = "Internal Use"
        )
    ),
    servers = [
        Server(
            url = "http://localhost:8080",
            description = "Local development server"
        )
    ]
)
@SecurityScheme(
    name = "bearerAuth",
    type = SecuritySchemeType.HTTP,
    scheme = "bearer",
    bearerFormat = "JWT",
    `in` = SecuritySchemeIn.HEADER,
    description = "JWT bearer token authorization, if security is enabled for this API."
)
class OpenApiConfig {
    @Bean
    fun kotlinModule() = tools.jackson.module.kotlin.KotlinModule.Builder().build()
}


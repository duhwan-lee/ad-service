package dev.duhwan.pay.persistence.config

import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.context.annotation.Configuration
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories

@Configuration
@EntityScan("dev.duhwan.pay.persistence.entity")
@EnableR2dbcRepositories("dev.duhwan.pay.persistence.repository")
class R2dbcConfig {
}
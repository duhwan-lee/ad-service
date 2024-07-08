package dev.duhwan.pay.persistence.config

import io.r2dbc.h2.H2ConnectionFactory
import io.r2dbc.h2.H2ConnectionOption
import io.r2dbc.spi.ConnectionFactory
import org.springframework.boot.SpringBootConfiguration
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.data.r2dbc.config.AbstractR2dbcConfiguration

@SpringBootConfiguration
@ComponentScan("dev.duhwan.pay.persistence")
@EnableAutoConfiguration
class MySqlConnectionConfig : AbstractR2dbcConfiguration() {
    @Bean
    override fun connectionFactory(): ConnectionFactory {
        return H2ConnectionFactory.inMemory("testdb", "sa", "", mapOf(H2ConnectionOption.MODE to "MYSQL"))
    }
}
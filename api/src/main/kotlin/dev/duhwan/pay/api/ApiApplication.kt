package dev.duhwan.pay.api

import io.swagger.v3.oas.annotations.OpenAPIDefinition
import io.swagger.v3.oas.annotations.info.Info
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@OpenAPIDefinition(info = Info(title = "Advertisement Service API", description = "광고 서비스"))
@SpringBootApplication(scanBasePackages = ["dev.duhwan.pay"])
class ApiApplication

fun main(args: Array<String>) {
    runApplication<ApiApplication>(*args)
}

object LocalDateTimeSerializer : KSerializer<LocalDateTime> {
    private val pattern = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("kotlinx.datetime.LocalDateTime", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, value: LocalDateTime) {
        encoder.encodeString(value.toString())
    }

    override fun deserialize(decoder: Decoder): LocalDateTime {
        return LocalDateTime.parse(decoder.decodeString(), pattern)
    }
}
package dev.uliana.zoo_sitter_service.common

import java.sql.Timestamp
import org.springframework.http.HttpStatusCode

data class ErrorMessage(
        val statusCode: HttpStatusCode,

        val timestamp: Timestamp,

        val message: String?
)

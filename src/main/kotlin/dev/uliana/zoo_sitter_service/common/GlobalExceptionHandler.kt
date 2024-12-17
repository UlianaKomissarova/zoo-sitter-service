package dev.uliana.zoo_sitter_service.common

import dev.uliana.zoo_sitter_service.util.exception.UserNotFoundException
import java.sql.Timestamp
import java.time.LocalDateTime
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatusCode
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {
    @ExceptionHandler(UserNotFoundException::class)
    fun handleNotFoundException(exception: UserNotFoundException): ResponseEntity<ErrorMessage> {
        return getErrorResponse(exception, HttpStatus.NOT_FOUND)
    }

    @ExceptionHandler(value = [DataIntegrityViolationException::class, MethodArgumentNotValidException::class])
    fun handleNotValidException(exception: Exception): ResponseEntity<ErrorMessage> {
        return getErrorResponse(exception, HttpStatus.BAD_REQUEST)
    }

    private fun getErrorResponse(exception: Exception, statusCode: HttpStatusCode): ResponseEntity<ErrorMessage> {
        val errorMessage = ErrorMessage(
                statusCode,
                Timestamp.valueOf(LocalDateTime.now()),
                exception.localizedMessage
        )

        return ResponseEntity(errorMessage, statusCode)
    }
}
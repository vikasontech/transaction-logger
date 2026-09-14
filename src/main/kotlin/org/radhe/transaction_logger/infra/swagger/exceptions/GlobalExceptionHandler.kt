package org.radhe.transaction_logger.infra.swagger.exceptions

import jakarta.servlet.http.HttpServletRequest
import org.radhe.transaction_logger.sessions.domain.vo.InvalidFieldException
import org.springframework.context.MessageSource
import java.time.Instant
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import java.util.Locale

@RestControllerAdvice
class GlobalExceptionHandler(
    private val messageSource: MessageSource
) {
    @ExceptionHandler(ResourceNotFoundException::class)
    fun handleResourceNotFound(
        ex: ResourceNotFoundException,
        request: HttpServletRequest
    ): ResponseEntity<ErrorResponse> {

        return ResponseEntity
            .status(HttpStatus.NOT_FOUND)
            .body(
                ErrorResponse(
                    status = HttpStatus.NOT_FOUND.value(),
                    error = HttpStatus.NOT_FOUND.reasonPhrase,
                    message = ex.message ?: "Resource not found",
                    path = request.requestURI
                )
            )
    }


    @ExceptionHandler(InvalidFieldException::class)
    fun handleInvalidField(ex: InvalidFieldException, locale: Locale): ResponseEntity<Map<String, String>> {
        val message = messageSource.getMessage(ex.code, ex.args as Array<out Any>?, locale)
        return ResponseEntity.badRequest().body(mapOf("error" to message))
    }


    @ExceptionHandler(RuntimeException::class)
    fun handleIllegalArgument(
        ex: RuntimeException,
        request: HttpServletRequest
    ): ResponseEntity<ErrorResponse> {

        return ResponseEntity
            .badRequest()
            .body(
                ErrorResponse(
                    status = HttpStatus.BAD_REQUEST.value(),
                    error = HttpStatus.BAD_REQUEST.reasonPhrase,
                    message = ex.message ?: "Invalid request",
                    path = request.requestURI
                )
            )
    }

    @ExceptionHandler(Exception::class)
    fun handleGenericException(
        ex: Exception,
        request: HttpServletRequest
    ): ResponseEntity<ErrorResponse> {

        return ResponseEntity
            .status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(
                ErrorResponse(
                    status = HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    error = HttpStatus.INTERNAL_SERVER_ERROR.reasonPhrase,
                    message = "An unexpected error occurred",
                    path = request.requestURI
                )
            )
    }
}

class ResourceNotFoundException(
    message: String
) : RuntimeException(message){}

data class ErrorResponse(
    val timestamp: Instant = Instant.now(),
    val status: Int,
    val error: String,
    val message: String,
    val path: String
)
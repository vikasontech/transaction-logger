package org.radhe.transaction_logger.transactions.api

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.ExampleObject
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.parameters.RequestBody as SwaggerRequestBody
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import org.radhe.transaction_logger.sessions.domain.vo.SessionId
import org.radhe.transaction_logger.tags.domain.model.Tags
import org.radhe.transaction_logger.transactions.application.TransactionsUseCase
import org.radhe.transaction_logger.transactions.domain.model.Day
import org.radhe.transaction_logger.transactions.domain.model.TransactionType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.math.BigDecimal

@RestController
@Tag(name = "Transactions", description = "Create transaction logs for the currently selected session and tag")
@RequestMapping("/api/transactions")
class TransactionLogController(
    private val transactionsUseCase: TransactionsUseCase
){
    @PostMapping
    @Operation(
        summary = "Create a transaction",
        description = "Creates an income, expense, or internal transfer transaction for an existing session. The tag must already exist."
    )
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Transaction created successfully"),
            ApiResponse(responseCode = "400", description = "Invalid transaction payload, missing tag, or invalid date", content = [Content(mediaType = "application/json")]),
            ApiResponse(responseCode = "404", description = "Session or tag not found", content = [Content(mediaType = "application/json")]),
            ApiResponse(responseCode = "500", description = "Unexpected server error", content = [Content(mediaType = "application/json")])
        ]
    )
    fun createTag(
        @SwaggerRequestBody(
            description = "Transaction details to log",
            required = true,
            content = [Content(
                mediaType = "application/json",
                schema = Schema(implementation = TransactionLogRequest::class),
                examples = [ExampleObject(
                    name = "Expense transaction",
                    value = """
                        {
                          "sessionId": 1,
                          "tag": "food",
                          "day": {
                            "value": 14
                          },
                          "transactionType": "EXPENSE",
                          "amount": 350.75,
                          "description": "Lunch and coffee"
                        }
                    """
                )]
            )]
        )
        @RequestBody request: TransactionLogRequest
    ): ResponseEntity<Tags> {
        transactionsUseCase.createTransaction(request)
        return ResponseEntity.ok().build()
    }
}

data class TransactionLogRequest(
    val sessionId: SessionId,
    val tag: String,
    val day: Day,
    val transactionType: TransactionType,
    val amount: BigDecimal,
    val description: String
) {
}
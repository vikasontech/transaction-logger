package org.radhe.transaction_logger.accounts.api

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.ExampleObject
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.parameters.RequestBody as SwaggerRequestBody
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import org.radhe.transaction_logger.accounts.application.AccountUseCase
import org.radhe.transaction_logger.accounts.domain.vo.AccountVo
import org.radhe.transaction_logger.accounts.infrastructure.persistence.AccountEntity
import org.radhe.transaction_logger.sessions.domain.vo.AccountId
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@Tag(name = "Accounts", description = "Create, update, and list bank accounts used for transaction logging")
@RequestMapping("/api/v1/accounts")
class AccountController(
    private val accountUseCase: AccountUseCase
){
    @PostMapping
    @Operation(
        summary = "Create an account",
        description = "Creates a bank account with a currency, account type, and optional email contact. The generated account id is returned in the response."
    )
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "Account created successfully",
                content = [Content(mediaType = "application/json", schema = Schema(implementation = AccountEntity::class))]
            ),
            ApiResponse(responseCode = "400", description = "Invalid account payload", content = [Content(mediaType = "application/json")]),
            ApiResponse(responseCode = "500", description = "Unexpected server error", content = [Content(mediaType = "application/json")])
        ]
    )
    fun createAccount(
        @SwaggerRequestBody(
            description = "Account details to create",
            required = true,
            content = [Content(
                mediaType = "application/json",
                schema = Schema(implementation = AccountVo::class),
                examples = [ExampleObject(
                    name = "Thai current account",
                    value = """
                        {
                          "accountName": "kbank",
                          "description": "Thai savings account",
                          "currency": "THB",
                          "accountType": "CURRENT",
                          "email": "vikas.on@gmail.com"
                        }
                    """
                )]
            )]
        )
        @RequestBody accountVo: AccountVo
    ): ResponseEntity<AccountEntity> {
        return ResponseEntity.ok(accountUseCase.createAccount(accountVo))
    }

    @GetMapping
    @Operation(
        summary = "List accounts",
        description = "Returns all accounts configured in the transaction logger."
    )
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Accounts returned successfully", content = [Content(mediaType = "application/json")]),
            ApiResponse(responseCode = "500", description = "Unexpected server error", content = [Content(mediaType = "application/json")])
        ]
    )
    fun queryAllAccounts(): ResponseEntity<List<AccountEntity>> {
        return ResponseEntity.ok(accountUseCase.getAllAccounts())
    }

    @PutMapping("/{id}")
    @Operation(
        summary = "Update an account",
        description = "Updates the account identified by the path id using the supplied account details."
    )
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Account updated successfully", content = [Content(mediaType = "application/json", schema = Schema(implementation = AccountEntity::class))]),
            ApiResponse(responseCode = "400", description = "Invalid account id or payload", content = [Content(mediaType = "application/json")]),
            ApiResponse(responseCode = "404", description = "Account not found", content = [Content(mediaType = "application/json")]),
            ApiResponse(responseCode = "500", description = "Unexpected server error", content = [Content(mediaType = "application/json")])
        ]
    )
    fun updateAccount(
        @Parameter(description = "Account id returned by account creation", example = "8aa90268-8a24-4d85-84a7-d2e7e70ece34", required = true)
        @PathVariable id: String,
        @SwaggerRequestBody(
            description = "Updated account details",
            required = true,
            content = [Content(mediaType = "application/json", schema = Schema(implementation = AccountVo::class))]
        )
        @RequestBody accountVo: AccountVo
    ): ResponseEntity<AccountEntity> {

        return ResponseEntity.ok(accountUseCase.updateAccount(AccountId(id), accountVo))
    }
}
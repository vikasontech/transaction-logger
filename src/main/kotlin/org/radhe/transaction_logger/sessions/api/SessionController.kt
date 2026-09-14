package org.radhe.transaction_logger.sessions.api

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.ExampleObject
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.parameters.RequestBody as SwaggerRequestBody
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import org.radhe.transaction_logger.sessions.application.SessionUseCases
import org.radhe.transaction_logger.sessions.domain.vo.CreateSessionRequest
import org.radhe.transaction_logger.sessions.domain.vo.SessionId
import org.radhe.transaction_logger.sessions.infrastructure.persistence.SessionEntity
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@Tag(name = "Sessions", description = "Open, close, and query account sessions for a monthly transaction period")
@RequestMapping("/api/v1/sessions")
class SessionController(
    private val sessionUseCases: SessionUseCases
) {

    @PostMapping
    @Operation(
        summary = "Create a session",
        description = "Starts a session for an account and month. Year must be between 2001 and 2027, and month must be between 1 and 12."
    )
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Session created successfully", content = [Content(mediaType = "application/json", schema = Schema(implementation = SessionId::class))]),
            ApiResponse(responseCode = "400", description = "Invalid account id, session period, or duplicate active session", content = [Content(mediaType = "application/json")]),
            ApiResponse(responseCode = "500", description = "Unexpected server error", content = [Content(mediaType = "application/json")])
        ]
    )
    fun createSession(
        @SwaggerRequestBody(
            description = "Account and month for the new session",
            required = true,
            content = [Content(
                mediaType = "application/json",
                schema = Schema(implementation = CreateSessionRequest::class),
                examples = [ExampleObject(
                    name = "Create September 2026 session",
                    value = """
                        {
                          "accountId": "8aa90268-8a24-4d85-84a7-d2e7e70ece34",
                          "sessionPeriod": {
                            "year": 2026,
                            "month": 9
                          }
                        }
                    """
                )]
            )]
        )
        @RequestBody createSessionRequest: CreateSessionRequest
    ): ResponseEntity<SessionId> {
        val create = sessionUseCases.create(createSessionRequest)
        return ResponseEntity.ok(create.sessionId!!)
    }
    @DeleteMapping("/{sessionId}")
    @Operation(
        summary = "Close a session",
        description = "Closes the session identified by the path id so it is no longer active."
    )
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Session closed successfully"),
            ApiResponse(responseCode = "400", description = "Invalid session id", content = [Content(mediaType = "application/json")]),
            ApiResponse(responseCode = "404", description = "Session not found", content = [Content(mediaType = "application/json")]),
            ApiResponse(responseCode = "500", description = "Unexpected server error", content = [Content(mediaType = "application/json")])
        ]
    )
    fun deleteSession(
        @Parameter(description = "Numeric session id returned when the session was created", example = "1", required = true)
        @PathVariable sessionId: SessionId
    ): ResponseEntity<SessionId> {
         sessionUseCases.closeSession(sessionId)
        return ResponseEntity.ok().build()
    }
    @GetMapping("/active")
    @Operation(
        summary = "List active sessions",
        description = "Returns every session that has not been closed yet."
    )
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Active sessions returned successfully", content = [Content(mediaType = "application/json")]),
            ApiResponse(responseCode = "500", description = "Unexpected server error", content = [Content(mediaType = "application/json")])
        ]
    )
    fun findAllActiveSessions(): ResponseEntity<List<SessionEntity>> {
        return ResponseEntity.ok(sessionUseCases.listAllActiveSessions())
    }

}


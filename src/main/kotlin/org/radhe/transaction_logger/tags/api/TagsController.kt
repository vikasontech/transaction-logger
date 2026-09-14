package org.radhe.transaction_logger.tags.api

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.ExampleObject
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.parameters.RequestBody as SwaggerRequestBody
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import org.radhe.transaction_logger.tags.application.TagsUseCase
import org.radhe.transaction_logger.tags.domain.model.Tags
import org.radhe.transaction_logger.tags.domain.vo.TagVo
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@Tag(name = "Tags", description = "Create tags used to categorize transaction logs")
@RequestMapping("/api/v1/tags")
class TagsController(
    private val tagsUseCase: TagsUseCase
){
    @PostMapping
    @Operation(
        summary = "Create a tag",
        description = "Creates a reusable tag for classifying income, expense, or transfer transactions. Tag names must be unique and non-blank."
    )
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Tag created successfully", content = [Content(mediaType = "application/json", schema = Schema(implementation = Tags::class))]),
            ApiResponse(responseCode = "400", description = "Invalid tag payload or duplicate tag name", content = [Content(mediaType = "application/json")]),
            ApiResponse(responseCode = "500", description = "Unexpected server error", content = [Content(mediaType = "application/json")])
        ]
    )
    fun createTag(
        @SwaggerRequestBody(
            description = "Tag details to create",
            required = true,
            content = [Content(
                mediaType = "application/json",
                schema = Schema(implementation = TagVo::class),
                examples = [ExampleObject(
                    name = "Food tag",
                    value = """
                        {
                          "name": "food",
                          "description": "Meals, groceries, and cafe spending"
                        }
                    """
                )]
            )]
        )
        @RequestBody tags: TagVo
    ): ResponseEntity<Tags> {
        return ResponseEntity.ok(tagsUseCase.handle(tags).toDomain())
    }
}
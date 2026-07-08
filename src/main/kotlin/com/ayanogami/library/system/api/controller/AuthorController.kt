package com.ayanogami.library.system.api.controller

import com.ayanogami.library.system.api.service.AuthorService
import com.ayanogami.library.system.api.view.AuthorResponse
import com.ayanogami.library.system.api.view.CreateAuthorRequest
import com.ayanogami.library.system.api.view.ErrorResponse
import com.ayanogami.library.system.api.view.UpdateAuthorRequest
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/authors")
@Tag(name = "Authors", description = "著者API")
class AuthorController(
    private val authorService: AuthorService,
) {
    @PostMapping
    @Operation(summary = "著者を作成する")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "201", description = "Created"),
            ApiResponse(
                responseCode = "400",
                description = "Bad Request",
                content = [Content(schema = Schema(implementation = ErrorResponse::class))],
            ),
        ],
    )
    fun create(
        @Valid @RequestBody request: CreateAuthorRequest,
    ): ResponseEntity<AuthorResponse> {
        val author = authorService.create(request.name, request.birthDate)

        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(AuthorResponse.from(author))
    }

    @PatchMapping("/{authorId}")
    @Operation(summary = "著者を更新する")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "OK"),
            ApiResponse(
                responseCode = "400",
                description = "Bad Request",
                content = [Content(schema = Schema(implementation = ErrorResponse::class))],
            ),
            ApiResponse(
                responseCode = "404",
                description = "Not Found",
                content = [Content(schema = Schema(implementation = ErrorResponse::class))],
            ),
        ],
    )
    fun update(
        @Parameter(description = "著者ID", example = "1")
        @PathVariable
        authorId: Long,
        @Valid @RequestBody request: UpdateAuthorRequest,
    ): ResponseEntity<AuthorResponse> {
        val author = authorService.update(authorId, request.name, request.birthDate)

        return ResponseEntity.ok(AuthorResponse.from(author))
    }
}

package com.ayanogami.library.system.api.controller

import com.ayanogami.library.system.api.service.AuthorService
import com.ayanogami.library.system.api.service.BookService
import com.ayanogami.library.system.api.view.AuthorBooksResponse
import com.ayanogami.library.system.api.view.BookResponse
import com.ayanogami.library.system.api.view.CreateBookRequest
import com.ayanogami.library.system.api.view.ErrorResponse
import com.ayanogami.library.system.api.view.UpdateBookRequest
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
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/books")
@Tag(name = "Books", description = "書籍API")
class BookController(
    private val bookService: BookService,
    private val authorService: AuthorService,
) {
    @GetMapping
    @Operation(summary = "著者に紐づく書籍一覧を取得する")
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
    fun findByAuthor(
        @Parameter(description = "著者ID", example = "1")
        @RequestParam
        authorId: Long,
    ): ResponseEntity<AuthorBooksResponse> {
        val authorBooks = authorService.findBooks(authorId)

        return ResponseEntity.ok(AuthorBooksResponse.from(authorBooks))
    }

    @PostMapping
    @Operation(summary = "書籍を作成する")
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
        @Valid @RequestBody request: CreateBookRequest,
    ): ResponseEntity<BookResponse> {
        val book =
            bookService.create(
                title = request.title,
                price = request.price,
                authorIds = request.authorIds,
                publicationStatus = request.publicationStatus,
            )

        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(BookResponse.from(book))
    }

    @PatchMapping("/{bookId}")
    @Operation(summary = "書籍を更新する")
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
        @Parameter(description = "書籍ID", example = "1")
        @PathVariable
        bookId: Long,
        @Valid @RequestBody request: UpdateBookRequest,
    ): ResponseEntity<BookResponse> {
        val book =
            bookService.update(
                id = bookId,
                title = request.title,
                price = request.price,
                authorIds = request.authorIds,
                publicationStatus = request.publicationStatus,
            )

        return ResponseEntity.ok(BookResponse.from(book))
    }
}

package com.ayanogami.library.system.api.author.controller

import com.ayanogami.library.system.api.author.service.AuthorService
import com.ayanogami.library.system.api.author.view.AuthorResponse
import com.ayanogami.library.system.api.author.view.CreateAuthorRequest
import com.ayanogami.library.system.api.author.view.UpdateAuthorRequest
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/authors")
class AuthorController(
	private val authorService: AuthorService,
) {
	@PostMapping
	fun create(@Valid @RequestBody request: CreateAuthorRequest): ResponseEntity<AuthorResponse> {
		val author = authorService.create(request.name, request.birthDate)

		return ResponseEntity
			.status(HttpStatus.CREATED)
			.body(AuthorResponse.from(author))
	}

	@PutMapping("/{authorId}")
	fun update(
		@PathVariable authorId: Long,
		@Valid @RequestBody request: UpdateAuthorRequest,
	): ResponseEntity<AuthorResponse> {
		val author = authorService.update(authorId, request.name, request.birthDate)

		return ResponseEntity.ok(AuthorResponse.from(author))
	}
}

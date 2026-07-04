package com.ayanogami.library.system.api.author

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDate

@RestController
@RequestMapping("/authors")
class AuthorController(
	private val authorService: AuthorService,
) {
	@PostMapping
	fun create(@RequestBody request: CreateAuthorRequest): ResponseEntity<AuthorResponse> {
		val author = authorService.create(request.name, request.birthDate)

		return ResponseEntity
			.status(HttpStatus.CREATED)
			.body(AuthorResponse.from(author))
	}
}

data class CreateAuthorRequest(
	val name: String,
	val birthDate: LocalDate,
)

data class AuthorResponse(
	val id: Long,
	val name: String,
	val birthDate: LocalDate,
) {
	companion object {
		fun from(author: Author): AuthorResponse =
			AuthorResponse(
				id = author.id,
				name = author.name,
				birthDate = author.birthDate,
			)
	}
}

@ResponseStatus(HttpStatus.BAD_REQUEST)
class InvalidAuthorException(message: String) : RuntimeException(message)

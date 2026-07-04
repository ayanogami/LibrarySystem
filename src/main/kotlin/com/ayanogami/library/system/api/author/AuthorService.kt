package com.ayanogami.library.system.api.author

import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class AuthorService(
	private val authorRepository: AuthorRepository,
) {
	fun create(name: String, birthDate: LocalDate): Author {
		if (name.isBlank()) {
			throw InvalidAuthorException("name is required")
		}

		if (birthDate.isAfter(LocalDate.now())) {
			throw InvalidAuthorException("birthDate must be today or earlier")
		}

		return authorRepository.create(name, birthDate)
	}
}

data class Author(
	val id: Long,
	val name: String,
	val birthDate: LocalDate,
)

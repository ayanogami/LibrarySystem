package com.ayanogami.library.system.api.author.service

import com.ayanogami.library.system.api.author.exception.AuthorNotFoundException
import com.ayanogami.library.system.api.author.exception.InvalidAuthorException
import com.ayanogami.library.system.api.author.model.Author
import com.ayanogami.library.system.api.author.repository.AuthorRepository
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class AuthorService(
	private val authorRepository: AuthorRepository,
) {
	fun create(name: String, birthDate: LocalDate): Author {
		validate(name, birthDate)

		return authorRepository.create(name, birthDate)
	}

	fun update(id: Long, name: String, birthDate: LocalDate): Author {
		validate(name, birthDate)

		return authorRepository.update(id, name, birthDate)
			?: throw AuthorNotFoundException(id)
	}

	private fun validate(name: String, birthDate: LocalDate) {
		if (name.isBlank()) {
			throw InvalidAuthorException("name is required")
		}

		if (birthDate.isAfter(LocalDate.now())) {
			throw InvalidAuthorException("birthDate must be today or earlier")
		}
	}
}

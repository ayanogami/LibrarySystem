package com.ayanogami.library.system.api.author

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import java.time.LocalDate

class AuthorServiceSpec : FunSpec({
	test("create returns a created author") {
		val repository = RecordingAuthorRepository()
		val service = AuthorService(repository)
		val birthDate = LocalDate.of(1867, 2, 9)

		val author = service.create("夏目漱石", birthDate)

		author shouldBe Author(
			id = 1,
			name = "夏目漱石",
			birthDate = birthDate,
		)
		repository.createdName shouldBe "夏目漱石"
		repository.createdBirthDate shouldBe birthDate
	}

	test("create accepts today's birth date") {
		val repository = RecordingAuthorRepository()
		val service = AuthorService(repository)
		val today = LocalDate.now()

		val author = service.create("今日 生まれ", today)

		author.birthDate shouldBe today
		repository.createdBirthDate shouldBe today
	}

	test("create rejects blank name") {
		val repository = RecordingAuthorRepository()
		val service = AuthorService(repository)

		val exception = shouldThrow<InvalidAuthorException> {
			service.create("   ", LocalDate.of(1867, 2, 9))
		}

		exception.message shouldBe "name is required"
		repository.called shouldBe false
	}

	test("create rejects future birth date") {
		val repository = RecordingAuthorRepository()
		val service = AuthorService(repository)
		val tomorrow = LocalDate.now().plusDays(1)

		val exception = shouldThrow<InvalidAuthorException> {
			service.create("未来 太郎", tomorrow)
		}

		exception.message shouldBe "birthDate must be today or earlier"
		repository.called shouldBe false
	}
})

private class RecordingAuthorRepository : AuthorRepository {
	var called = false
	var createdName: String? = null
	var createdBirthDate: LocalDate? = null

	override fun create(name: String, birthDate: LocalDate): Author {
		called = true
		createdName = name
		createdBirthDate = birthDate

		return Author(
			id = 1,
			name = name,
			birthDate = birthDate,
		)
	}
}

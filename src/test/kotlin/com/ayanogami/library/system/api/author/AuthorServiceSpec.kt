package com.ayanogami.library.system.api.author

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import java.time.LocalDate

class AuthorServiceSpec : DescribeSpec({
	describe("create") {
		context("when the request is valid") {
			it("returns a created author") {
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
		}

		context("when birth date is today") {
			it("returns a created author") {
				val repository = RecordingAuthorRepository()
				val service = AuthorService(repository)
				val today = LocalDate.now()

				val author = service.create("今日 生まれ", today)

				author.birthDate shouldBe today
				repository.createdBirthDate shouldBe today
			}
		}

		context("when name is blank") {
			it("throws InvalidAuthorException") {
				val repository = RecordingAuthorRepository()
				val service = AuthorService(repository)

				val exception = shouldThrow<InvalidAuthorException> {
					service.create("   ", LocalDate.of(1867, 2, 9))
				}

				exception.message shouldBe "name is required"
				repository.called shouldBe false
			}
		}

		context("when birth date is future") {
			it("throws InvalidAuthorException") {
				val repository = RecordingAuthorRepository()
				val service = AuthorService(repository)
				val tomorrow = LocalDate.now().plusDays(1)

				val exception = shouldThrow<InvalidAuthorException> {
					service.create("未来 太郎", tomorrow)
				}

				exception.message shouldBe "birthDate must be today or earlier"
				repository.called shouldBe false
			}
		}
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

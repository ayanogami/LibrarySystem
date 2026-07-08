package com.ayanogami.library.system.api.view

import com.ayanogami.library.system.api.model.AuthorBooks
import io.swagger.v3.oas.annotations.media.Schema
import java.time.LocalDate

@Schema(description = "著者に紐づく書籍一覧レスポンス")
data class AuthorBooksResponse(
	@field:Schema(description = "著者ID", example = "1")
	val id: Long,

	@field:Schema(description = "著者名", example = "夏目漱石")
	val name: String,

	@field:Schema(description = "生年月日", example = "1867-02-09")
	val birthDate: LocalDate,

	@field:Schema(description = "書籍一覧")
	val books: List<AuthorBookResponse>,
) {
	companion object {
		fun from(authorBooks: AuthorBooks): AuthorBooksResponse =
			AuthorBooksResponse(
				id = authorBooks.author.id,
				name = authorBooks.author.name,
				birthDate = authorBooks.author.birthDate,
				books = authorBooks.books.map(AuthorBookResponse::from),
			)
	}
}

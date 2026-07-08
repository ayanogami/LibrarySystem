package com.ayanogami.library.system.api.view

import com.ayanogami.library.system.api.model.Book
import com.ayanogami.library.system.api.model.PublicationStatus
import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "書籍レスポンス")
data class BookResponse(
	@field:Schema(description = "書籍ID", example = "1")
	val id: Long,

	@field:Schema(description = "タイトル", example = "吾輩は猫である")
	val title: String,

	@field:Schema(description = "価格", example = "1200")
	val price: Int,

	@field:Schema(description = "出版状況", example = "PUBLISHED")
	val publicationStatus: PublicationStatus,

	@field:Schema(description = "著者一覧")
	val authors: List<BookAuthorResponse>,
) {
	companion object {
		fun from(book: Book): BookResponse =
			BookResponse(
				id = book.id,
				title = book.title,
				price = book.price,
				publicationStatus = book.publicationStatus,
				authors = book.authors.map(BookAuthorResponse::from),
			)
	}
}

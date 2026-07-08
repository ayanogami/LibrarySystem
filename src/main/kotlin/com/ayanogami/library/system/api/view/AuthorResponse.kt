package com.ayanogami.library.system.api.view

import com.ayanogami.library.system.api.model.Author
import io.swagger.v3.oas.annotations.media.Schema
import java.time.LocalDate

@Schema(description = "著者レスポンス")
data class AuthorResponse(
	@field:Schema(description = "著者ID", example = "1")
	val id: Long,

	@field:Schema(description = "著者名", example = "夏目漱石")
	val name: String,

	@field:Schema(description = "生年月日", example = "1867-02-09")
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

package com.ayanogami.library.system.api.view

import com.ayanogami.library.system.api.model.PublicationStatus
import io.swagger.v3.oas.annotations.media.ArraySchema
import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.PositiveOrZero

@Schema(description = "書籍作成リクエスト")
data class CreateBookRequest(
	@field:Schema(description = "タイトル", example = "吾輩は猫である")
	@field:NotBlank
	val title: String,

	@field:Schema(description = "価格。0以上を指定します。", example = "1200")
	@field:PositiveOrZero
	val price: Int,

	@field:ArraySchema(
		schema = Schema(description = "著者ID", example = "1"),
		minItems = 1,
	)
	@field:NotEmpty
	val authorIds: List<Long>,

	@field:Schema(description = "出版状況", example = "PUBLISHED")
	val publicationStatus: PublicationStatus,
)

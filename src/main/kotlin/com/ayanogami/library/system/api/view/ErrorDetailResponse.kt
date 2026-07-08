package com.ayanogami.library.system.api.view

import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "エラー詳細")
data class ErrorDetailResponse(
	@field:Schema(description = "エラー対象フィールド", example = "name")
	val field: String,

	@field:Schema(description = "エラーメッセージ", example = "must not be blank")
	val message: String,
)

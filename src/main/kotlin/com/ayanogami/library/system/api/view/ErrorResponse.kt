package com.ayanogami.library.system.api.view

import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "共通エラーレスポンス")
data class ErrorResponse(
	@field:Schema(description = "エラーコード", example = "VALIDATION_ERROR")
	val code: String,

	@field:Schema(description = "エラーメッセージ", example = "validation failed")
	val message: String,

	@field:Schema(description = "エラー詳細一覧")
	val details: List<ErrorDetailResponse> = emptyList(),
)

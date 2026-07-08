package com.ayanogami.library.system.api.view

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.PastOrPresent
import java.time.LocalDate

@Schema(description = "著者更新リクエスト。nameまたはbirthDateの少なくとも一方を指定します。")
data class UpdateAuthorRequest(
	@field:Schema(description = "著者名", example = "夏目 金之助", nullable = true)
	val name: String?,

	@field:Schema(description = "生年月日", example = "1867-02-10", nullable = true)
	@field:PastOrPresent
	val birthDate: LocalDate?,
)

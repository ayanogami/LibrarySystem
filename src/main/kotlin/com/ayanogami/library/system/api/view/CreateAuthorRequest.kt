package com.ayanogami.library.system.api.view

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.PastOrPresent
import java.time.LocalDate

@Schema(description = "著者作成リクエスト")
data class CreateAuthorRequest(
    @field:Schema(description = "著者名", example = "夏目漱石")
    @field:NotBlank
    val name: String,
    @field:Schema(description = "生年月日", example = "1867-02-09")
    @field:PastOrPresent
    val birthDate: LocalDate,
)

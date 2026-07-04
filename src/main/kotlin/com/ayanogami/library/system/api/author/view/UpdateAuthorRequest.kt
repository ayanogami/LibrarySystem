package com.ayanogami.library.system.api.author.view

import jakarta.validation.constraints.PastOrPresent
import java.time.LocalDate

data class UpdateAuthorRequest(
	val name: String?,

	@field:PastOrPresent
	val birthDate: LocalDate?,
)

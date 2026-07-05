package com.ayanogami.library.system.api.view

import jakarta.validation.constraints.PastOrPresent
import java.time.LocalDate

data class UpdateAuthorRequest(
	val name: String?,

	@field:PastOrPresent
	val birthDate: LocalDate?,
)

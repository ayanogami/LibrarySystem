package com.ayanogami.library.system.api.model

import java.time.LocalDate

data class Author(
	val id: Long,
	val name: String,
	val birthDate: LocalDate,
)

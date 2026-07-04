package com.ayanogami.library.system.api.author.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.NOT_FOUND)
class AuthorNotFoundException(authorId: Long) : RuntimeException("author not found: $authorId")

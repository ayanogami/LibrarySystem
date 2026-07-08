package com.ayanogami.library.system.api.config

import io.swagger.v3.oas.annotations.OpenAPIDefinition
import io.swagger.v3.oas.annotations.info.Info
import org.springframework.context.annotation.Configuration

@Configuration
@OpenAPIDefinition(
    info =
        Info(
            title = "LibrarySystemAPI",
            version = "0.0.1",
            description = "書籍管理システムのバックエンドAPI",
        ),
)
class OpenApiConfig

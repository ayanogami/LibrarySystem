package com.ayanogami.library.system.api.view

import com.ayanogami.library.system.api.model.PublicationStatus
import io.swagger.v3.oas.annotations.media.ArraySchema
import io.swagger.v3.oas.annotations.media.Schema

@Schema(
	description = "書籍更新リクエスト。title、price、authorIds、publicationStatusの少なくとも一つを指定します。",
)
data class UpdateBookRequest(
	@field:Schema(description = "タイトル", example = "坊っちゃん", nullable = true)
	val title: String?,

	@field:Schema(description = "価格。0以上を指定します。", example = "1500", nullable = true)
	val price: Int?,

	@field:ArraySchema(
		schema = Schema(description = "著者ID", example = "1"),
		minItems = 1,
	)
	val authorIds: List<Long>?,

	@field:Schema(description = "出版状況。PUBLISHEDからUNPUBLISHEDには戻せません。", example = "PUBLISHED", nullable = true)
	val publicationStatus: PublicationStatus?,
)

package com.ayanogami.library.system.api.author

import com.ayanogami.library.system.api.jooq.generated.Tables.AUTHORS
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.extensions.spring.SpringExtension
import io.kotest.matchers.shouldBe
import org.jooq.DSLContext
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import java.time.LocalDate

@SpringBootTest
@AutoConfigureMockMvc
class AuthorApiSpec : DescribeSpec({
	extension(SpringExtension())

	lateinit var mockMvc: MockMvc
	lateinit var dsl: DSLContext

	beforeSpec { spec ->
		val authorApiSpec = spec as AuthorApiSpec
		mockMvc = authorApiSpec.mockMvc
		dsl = authorApiSpec.dsl
	}

	beforeTest {
		dsl.deleteFrom(AUTHORS).execute()
	}

	describe("POST /authors") {
		context("リクエストが妥当な場合") {
			it("著者を作成する") {
				mockMvc.perform(
					post("/authors")
						.contentType(MediaType.APPLICATION_JSON)
						.content(
							"""
							{
							  "name": "夏目漱石",
							  "birthDate": "1867-02-09"
							}
							""".trimIndent(),
						),
				)
					.andExpect(status().isCreated)
					.andExpect(jsonPath("$.id").isNumber)
					.andExpect(jsonPath("$.name").value("夏目漱石"))
					.andExpect(jsonPath("$.birthDate").value("1867-02-09"))

				val author = dsl.selectFrom(AUTHORS).fetchOne()

				author?.get(AUTHORS.NAME) shouldBe "夏目漱石"
				author?.get(AUTHORS.BIRTH_DATE).toString() shouldBe "1867-02-09"
			}
		}

		context("生年月日が現在日の場合") {
			it("著者を作成する") {
				val today = LocalDate.now()

				mockMvc.perform(
					post("/authors")
						.contentType(MediaType.APPLICATION_JSON)
						.content(
							"""
							{
							  "name": "今日 生まれ",
							  "birthDate": "$today"
							}
							""".trimIndent(),
						),
				)
					.andExpect(status().isCreated)
					.andExpect(jsonPath("$.name").value("今日 生まれ"))
					.andExpect(jsonPath("$.birthDate").value(today.toString()))

				val author = dsl.selectFrom(AUTHORS).fetchOne()

				author?.get(AUTHORS.BIRTH_DATE) shouldBe today
			}
		}

		context("著者名が空白の場合") {
			it("400 Bad Request を返す") {
				mockMvc.perform(
					post("/authors")
						.contentType(MediaType.APPLICATION_JSON)
						.content(
							"""
							{
							  "name": "   ",
							  "birthDate": "1867-02-09"
							}
							""".trimIndent(),
						),
				)
					.andExpect(status().isBadRequest)

				dsl.fetchCount(AUTHORS) shouldBe 0
			}
		}

		context("著者名が未指定の場合") {
			it("400 Bad Request を返す") {
				mockMvc.perform(
					post("/authors")
						.contentType(MediaType.APPLICATION_JSON)
						.content(
							"""
							{
							  "birthDate": "1867-02-09"
							}
							""".trimIndent(),
						),
				)
					.andExpect(status().isBadRequest)

				dsl.fetchCount(AUTHORS) shouldBe 0
			}
		}

		context("生年月日が未指定の場合") {
			it("400 Bad Request を返す") {
				mockMvc.perform(
					post("/authors")
						.contentType(MediaType.APPLICATION_JSON)
						.content(
							"""
							{
							  "name": "夏目漱石"
							}
							""".trimIndent(),
						),
				)
					.andExpect(status().isBadRequest)

				dsl.fetchCount(AUTHORS) shouldBe 0
			}
		}

		context("生年月日が現在日より後の場合") {
			it("400 Bad Request を返す") {
				mockMvc.perform(
					post("/authors")
						.contentType(MediaType.APPLICATION_JSON)
						.content(
							"""
							{
							  "name": "未来 太郎",
							  "birthDate": "2999-01-01"
							}
							""".trimIndent(),
						),
				)
					.andExpect(status().isBadRequest)

				dsl.fetchCount(AUTHORS) shouldBe 0
			}
		}
	}
}) {
	@Autowired
	lateinit var mockMvc: MockMvc

	@Autowired
	lateinit var dsl: DSLContext
}

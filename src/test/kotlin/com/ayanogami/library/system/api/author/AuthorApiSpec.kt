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
		context("when the request is valid") {
			it("creates an author") {
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

		context("when name is blank") {
			it("returns 400 Bad Request") {
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

		context("when birth date is future") {
			it("returns 400 Bad Request") {
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

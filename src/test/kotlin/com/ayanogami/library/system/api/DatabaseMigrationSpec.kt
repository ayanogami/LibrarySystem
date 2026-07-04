package com.ayanogami.library.system.api

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.extensions.spring.SpringExtension
import io.kotest.matchers.collections.shouldContainAll
import io.kotest.matchers.shouldBe
import org.jooq.DSLContext
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class DatabaseMigrationSpec : DescribeSpec({
	extension(SpringExtension())

	lateinit var dsl: DSLContext

	beforeSpec { spec ->
		dsl = (spec as DatabaseMigrationSpec).dsl
	}

	describe("Flyway マイグレーション") {
		it("書籍管理に必要なテーブルを作成する") {
			val tableNames = dsl
				.fetch(
					"""
					select table_name
					from information_schema.tables
					where table_schema = 'public'
					  and table_type = 'BASE TABLE'
					""".trimIndent(),
				)
				.map { it.get("table_name", String::class.java) }

			tableNames shouldContainAll listOf("authors", "books", "book_authors")
		}

		it("必要な制約を作成する") {
			val constraintNames = dsl
				.fetch(
					"""
					select constraint_name
					from information_schema.table_constraints
					where table_schema = 'public'
					""".trimIndent(),
				)
				.map { it.get("constraint_name", String::class.java) }

			constraintNames shouldContainAll listOf(
				"pk_authors",
				"chk_authors_birth_date",
				"pk_books",
				"chk_books_price",
				"chk_books_publication_status",
				"pk_book_authors",
				"fk_book_authors_book_id",
				"fk_book_authors_author_id",
			)
		}

		it("著者に紐づく書籍を取得するためのインデックスを作成する") {
			val indexExists = dsl
				.fetchOne(
					"""
					select exists (
					  select 1
					  from pg_indexes
					  where schemaname = 'public'
					    and tablename = 'book_authors'
					    and indexname = 'idx_book_authors_author_id'
					) as exists
					""".trimIndent(),
				)
				?.get("exists", Boolean::class.java)

			indexExists shouldBe true
		}
	}
}) {
	@Autowired
	lateinit var dsl: DSLContext
}

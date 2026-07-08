package com.ayanogami.library.system.api

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe

class ApiApplicationSpec :
    DescribeSpec({
        describe("Kotest の設定") {
            it("テストを実行できる") {
                "LibrarySystemAPI" shouldBe "LibrarySystemAPI"
            }
        }
    })

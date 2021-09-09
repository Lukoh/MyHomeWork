package com.goforer.myhomework.data.source.model.entity

import com.goforer.test.kit.DataClassTestTool
import org.junit.Assert
import org.junit.Test

class BaseTest {
    companion object {
        val mock by lazy {
            BaseTest()
        }

        fun getInstance() = mock
    }

    val responseResult0 = ResponseResult("OK")
    private val responseResult1 = ResponseResult("OK")

    @Test
    fun responseResultTest() {
        Assert.assertTrue(DataClassTestTool.checkProperties(responseResult0, responseResult1))
    }
}

package com.goforer.myhomework.data.source.model.entity.login

import com.goforer.myhomework.data.source.model.entity.ResponseResult

class LogInTest {
    companion object {
        val mock by lazy {
            LogInTest()
        }

        fun getInstance() = mock
    }

    val result0 = ResponseResult(
        "true",
        "Successful"
    )
}

package com.goforer.myhomework.data.source.model.entity.signup

import com.goforer.myhomework.data.source.model.entity.ResponseResult

class SignUpTest {
    companion object {
        val mock by lazy {
            SignUpTest()
        }

        fun getInstance() = mock
    }

    val result0 = ResponseResult(
        "true",
        "Successful"
    )
}

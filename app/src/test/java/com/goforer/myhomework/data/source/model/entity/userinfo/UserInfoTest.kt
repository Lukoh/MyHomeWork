package com.goforer.myhomework.data.source.model.entity.userinfo

import com.goforer.myhomework.data.source.model.entity.user.User
import com.goforer.myhomework.data.source.model.entity.user.response.UserInfo

class UserInfoTest {
    companion object {
        val mock by lazy {
            UserInfoTest()
        }

        fun getInstance() = mock
    }

    val userInfo0 = UserInfo(
        true,
        User(
            "aaaa",
            "a소개합니다.",
            0
        )
    )
}

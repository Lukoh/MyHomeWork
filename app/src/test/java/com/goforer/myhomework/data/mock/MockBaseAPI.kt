package com.goforer.myhomework.data.mock

import com.goforer.myhomework.data.source.model.entity.BaseTest
import com.goforer.myhomework.data.source.model.entity.cardinfo.CardInfoTest
import com.goforer.myhomework.data.source.model.entity.home.HomeTest
import com.goforer.myhomework.data.source.model.entity.login.LogInTest
import com.goforer.myhomework.data.source.model.entity.signup.SignUpTest
import com.goforer.myhomework.data.source.model.entity.userinfo.UserInfoTest
import com.goforer.myhomework.data.source.network.response.ApiResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response

open class MockBaseAPI(private val isMakeError: Boolean) {
    val baseTest = BaseTest()
    val homeTest = HomeTest()
    val cardInfoTest = CardInfoTest()
    val userInfoTest = UserInfoTest()
    val signUpTest = SignUpTest()
    val logInTest = LogInTest()

    fun <T> getFlowResponse(body: T): Flow<ApiResponse<T>> {
        return flow {
            if (isMakeError)
                emit(ApiResponse.create<T>(Throwable()))
            else
                emit(ApiResponse.create(Response.success(body)))
        }
    }
}

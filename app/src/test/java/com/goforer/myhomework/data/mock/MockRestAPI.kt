package com.goforer.myhomework.data.mock

import com.goforer.myhomework.data.source.model.entity.ResponseResult
import com.goforer.myhomework.data.source.model.entity.card.response.CardInfo
import com.goforer.myhomework.data.source.model.entity.home.response.Feed
import com.goforer.myhomework.data.source.model.entity.home.response.Home
import com.goforer.myhomework.data.source.model.entity.login.LogInData
import com.goforer.myhomework.data.source.model.entity.signup.SignUpData
import com.goforer.myhomework.data.source.model.entity.user.response.UserInfo
import com.goforer.myhomework.data.source.network.api.RestAPI
import com.goforer.myhomework.data.source.network.response.ApiResponse
import kotlinx.coroutines.flow.Flow

class MockRestAPI(isMakeError: Boolean = false) : RestAPI, MockBaseAPI(isMakeError) {
    override fun getFeed(
        page: Int,
        per_page: Int
    ): Flow<ApiResponse<Feed>> {
        return getFlowResponse(homeTest.feed0)
    }

    override fun getHome(): Flow<ApiResponse<Home>> {
        return getFlowResponse(homeTest.home0)
    }

    override fun getCardInfo(id: Int): Flow<ApiResponse<CardInfo>> {
        return getFlowResponse(cardInfoTest.cardInfo0)
    }

    override fun getUserInfo(id: Int): Flow<ApiResponse<UserInfo>> {
        return getFlowResponse(userInfoTest.userInfo0)
    }

    override fun signUp(signUpData: SignUpData): Flow<ApiResponse<ResponseResult>> {
        return getFlowResponse(signUpTest.result0)
    }

    override fun logIn(logInData: LogInData): Flow<ApiResponse<ResponseResult>> {
        return getFlowResponse(logInTest.result0)
    }
}

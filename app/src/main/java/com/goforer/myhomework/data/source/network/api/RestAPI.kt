/*
 * Copyright (C) 2021 Lukoh Nam, goForer
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 2 of the License,
 * or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License along with this program.
 * If not, see <http://www.gnu.org/licenses/>.
 */

package com.goforer.myhomework.data.source.network.api

import com.goforer.myhomework.data.source.model.entity.ResponseResult
import com.goforer.myhomework.data.source.model.entity.card.response.CardInfo
import com.goforer.myhomework.data.source.model.entity.home.response.Feed
import com.goforer.myhomework.data.source.model.entity.home.response.Home
import com.goforer.myhomework.data.source.model.entity.login.LogInData
import com.goforer.myhomework.data.source.model.entity.signup.SignUpData
import com.goforer.myhomework.data.source.model.entity.user.response.UserInfo
import com.goforer.myhomework.data.source.network.response.ApiResponse
import kotlinx.coroutines.flow.Flow
import retrofit2.http.*

interface RestAPI {
    @GET("cards")
    fun getFeed(
        @Query("page") page: Int,
        @Query("per_page") per_page: Int
    ): Flow<ApiResponse<Feed>>

    @GET("home")
    fun getHome(): Flow<ApiResponse<Home>>

    @GET("cards/{id}")
    fun getCardInfo(
        @Path("id") id: Int
    ): Flow<ApiResponse<CardInfo>>

    @GET("users/{id}")
    fun getUserInfo(
        @Path("id") id: Int
    ): Flow<ApiResponse<UserInfo>>

    @POST("users")
    fun signUp(
        @Body signUpData: SignUpData
    ): Flow<ApiResponse<ResponseResult>>

    @POST("users/sign_in")
    fun logIn(
        @Body logInData: LogInData
    ): Flow<ApiResponse<ResponseResult>>
}

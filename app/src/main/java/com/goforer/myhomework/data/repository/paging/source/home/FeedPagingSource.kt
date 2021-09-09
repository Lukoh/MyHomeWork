package com.goforer.myhomework.data.repository.paging.source.home

import androidx.paging.PagingState
import com.goforer.base.extension.isNullOnFlow
import com.goforer.myhomework.data.repository.paging.PagingErrorMessage.ERROR_MESSAGE_PAGING_EMPTY
import com.goforer.myhomework.data.repository.paging.source.BasePagingSource
import com.goforer.myhomework.data.source.model.entity.card.Card
import com.goforer.myhomework.data.source.model.entity.home.response.Feed
import com.goforer.myhomework.data.source.network.response.ApiEmptyResponse
import com.goforer.myhomework.data.source.network.response.ApiErrorResponse
import com.goforer.myhomework.data.source.network.response.ApiSuccessResponse
import com.goforer.myhomework.presentation.vm.Query
import kotlinx.coroutines.flow.collect
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FeedPagingSource
@Inject
constructor() : BasePagingSource<Int, Card>() {
    private var pagingFeed: Feed? = null

    companion object {
        internal var nextPage = 1
    }

    override fun setData(query: Query, value: Any) {
        this.query = query
        pagingFeed = value as Feed
    }

    @SuppressWarnings("unchecked")
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Card> {
        return try {
            params.key.isNullOnFlow({
                nextPage = 1
            }, {
                nextPage = it.plus(1)
                restAPI.getFeed(
                    it.plus(1),
                    query.secondParam as Int
                ).collect { apiResponse ->
                    pagingFeed = when (apiResponse) {
                        is ApiSuccessResponse -> {
                            apiResponse.body
                        }

                        is ApiEmptyResponse -> {
                            errorMessage = ERROR_MESSAGE_PAGING_EMPTY
                            null
                        }

                        is ApiErrorResponse -> {
                            errorMessage = apiResponse.errorMessage
                            null
                        }
                    }
                }
            })

            if (pagingFeed != null)
                LoadResult.Page(
                    data = pagingFeed?.cards!!,
                    prevKey = null,
                    nextKey = params.key?.plus(1) ?: 1
                )
            else
                LoadResult.Error(Throwable(errorMessage))
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        } catch (exception: Exception) {
            // Handle errors in this block
            return LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Card>): Int? {
        // Try to find the page key of the closest page to anchorPosition, from
        // either the prevKey or the nextKey, but you need to handle nullability
        // here:
        //  * prevKey == null -> anchorPage is the first page.
        //  * nextKey == null -> anchorPage is the last page.
        //  * both prevKey and nextKey null -> anchorPage is the initial page, so
        //    just return null.
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}

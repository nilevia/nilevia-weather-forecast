package com.nilevia.data.remote.utils

import com.nilevia.domain.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response

fun <ResponseType, ResultType> networkHandling(
    apiCall: suspend () -> Response<ResponseType>,
    mapper: (ResponseType) -> ResultType,
    saveToDb: ((data: ResponseType?) -> Unit)? = null // use for improvement if we want to save network response to database

): Flow<Resource<ResultType>> {

    return flow {
        emit(Resource(Resource.Status.LOADING))
        try {
            with(apiCall.invoke()) {
                when {
                    isSuccessful -> {
                        val result = body()?.let { mapper(it) }
                        emit(Resource(Resource.Status.SUCCESS, result))
                        saveToDb?.invoke(body())
                    }
                    else -> emit(Resource(Resource.Status.ERROR))
                }
            }
        } catch (e: Exception) {
            emit(Resource(Resource.Status.ERROR))
        }
    }
}
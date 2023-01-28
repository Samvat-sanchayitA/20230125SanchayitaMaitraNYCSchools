package com.example.nycschools.util

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*

inline fun <ResultType, RequestType> networkBoundResource(
    crossinline query: () -> Flow<ResultType>,
    crossinline fetch: suspend () -> RequestType,
    crossinline saveFetchResult: suspend (RequestType) -> Unit,
    crossinline shouldFetch: (ResultType) -> Boolean
) = flow {
    val data = query().first()

    val flow = if (shouldFetch(data)) {
        emit(UiState.Loading(data))

        try {
            saveFetchResult(fetch())
            query().map { UiState.Success(it) }
        } catch (throwable: Throwable) {
            query().map { UiState.Error(throwable, it) }
        }
    } else {
        query().map { UiState.Success(it) }
    }

    emitAll(flow)
}.flowOn(Dispatchers.IO)

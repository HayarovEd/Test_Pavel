package com.edurda77.test_pavel.domain.utils

sealed interface DataError : RootError {
    enum class Network: DataError {
        BAD_REQUEST,
        SERVER_ERROR,
        UNKNOWN
    }
}
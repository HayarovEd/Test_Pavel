package com.edurda77.test_pavel.ui.uikit

import com.edurda77.resources.uikit.UiText
import com.edurda77.test_pavel.R
import com.edurda77.test_pavel.domain.utils.DataError
import com.edurda77.test_pavel.domain.utils.ResultWork


fun DataError.asUiText(): UiText {
    return when (this) {



        DataError.Network.SERVER_ERROR -> UiText.StringResource(
            R.string.server_error
        )

        DataError.Network.UNKNOWN -> UiText.StringResource(
            R.string.unknown_error
        )

        DataError.Network.BAD_REQUEST -> {
            UiText.StringResource(
                R.string.not_unique_id
            )
        }
    }
}

fun ResultWork.Error<*, DataError>.asErrorUiText(): UiText {
    return error.asUiText()
}
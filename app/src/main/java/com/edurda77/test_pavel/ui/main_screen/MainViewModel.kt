package com.edurda77.test_pavel.ui.main_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.edurda77.test_pavel.domain.repository.RemoteRepository
import com.edurda77.test_pavel.domain.utils.ResultWork
import com.edurda77.test_pavel.ui.uikit.asUiText
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


@HiltViewModel
class MainViewModel @Inject constructor(
    private val remoteRepository: RemoteRepository
) : ViewModel() {

    private var _state = MutableStateFlow(MainState())
    val state = _state.asStateFlow()

    fun onEvent(event: MainEvent) {
        when (event) {
            is MainEvent.OnSearch -> {
                search()
            }

            is MainEvent.SetQuery -> {
                _state.value.copy(
                    query = event.query,
                )
                    .updateState()
            }
        }
    }

    private fun search() {
        _state.value.copy(
            isLoading = true,
            cities = emptyList()
        )
            .updateState()
        viewModelScope.launch {
            when (val result = remoteRepository.getGeoByName(state.value.query)) {
                is ResultWork.Error -> {
                    _state.value.copy(
                        isLoading = false,
                        message = result.error.asUiText()
                    )
                        .updateState()
                }

                is ResultWork.Success -> {
                    _state.value.copy(
                        isLoading = false,
                        cities = result.data
                    )
                        .updateState()
                }
            }
        }

    }

    private fun MainState.updateState() {
        _state.update {
            this
        }
    }
}
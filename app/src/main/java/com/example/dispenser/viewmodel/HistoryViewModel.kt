package com.example.dispenser.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dispenser.data.model.HistoryDto
import com.example.dispenser.data.repository.HistoryRepository
import com.example.dispenser.data.util.Result
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

data class HistoryUiState(
    val loading: Boolean = false,
    val page: Int = 0,
    val endReached: Boolean = false,
    val items: List<HistoryDto> = emptyList(),
    val error: String? = null
)

class HistoryViewModel(
    private val repository: HistoryRepository = HistoryRepository()
) : ViewModel() {

    companion object { private const val PAGE_SIZE = 20 }

    private val _ui = MutableStateFlow(HistoryUiState())
    val ui: StateFlow<HistoryUiState> = _ui

    fun loadAll(next: Boolean = false) {
        val newPage = if (next) _ui.value.page + 1 else 0
        if (_ui.value.loading || (_ui.value.endReached && next)) return

        viewModelScope.launch {
            _ui.value = _ui.value.copy(loading = true, error = null)

            when (val res = repository.getAll(newPage, PAGE_SIZE)) {
                is Result.Success -> {
                    val page = res.data
                    val merged = if (next) _ui.value.items + page.content else page.content
                    _ui.value = _ui.value.copy(
                        loading = false,
                        page = page.number,      // 응답의 현 페이지번호
                        endReached = page.last,  // 마지막 페이지 여부
                        items = merged,
                        error = null
                    )
                }
                is Result.Error -> {
                    _ui.value = _ui.value.copy(
                        loading = false,
                        error = res.message
                    )
                }
            }
        }
    }
}

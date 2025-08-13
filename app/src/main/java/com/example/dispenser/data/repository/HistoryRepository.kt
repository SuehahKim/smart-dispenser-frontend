package com.example.dispenser.data.repository

import com.example.dispenser.data.api.HistoryService
import com.example.dispenser.data.api.RetrofitClient
import com.example.dispenser.data.model.HistoryDto
import com.example.dispenser.data.model.PageResponse
import com.example.dispenser.data.util.Result

class HistoryRepository(
    private val service: HistoryService = RetrofitClient.historyService
) {
    // 전체 과거 이력
    suspend fun getAll(page: Int, size: Int): Result<PageResponse<HistoryDto>> = try {
        val data = service.getAllHistories(page, size)
        Result.Success(data)
    } catch (e: Exception) {
        Result.Error(e.message ?: "Network error", -1)
    }

    // 특정 디스펜서 과거 이력
    suspend fun getByDispenser(
        dispenserId: Long,
        page: Int,
        size: Int
    ): Result<PageResponse<HistoryDto>> = try {
        val data = service.getHistoriesByDispenser(dispenserId, page, size)
        Result.Success(data)
    } catch (e: Exception) {
        Result.Error(e.message ?: "Network error", -1)
    }
}

package com.example.dispenser.data.api

import com.example.dispenser.data.model.PageResponse
import com.example.dispenser.data.model.HistoryDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.Response

interface HistoryService {

    // 전체 과거 이력 조회  GET  /api/histories/me?page=0&size=20
    @GET("/api/histories/me")
    suspend fun getAllHistories(
        @Query("page") page: Int,
        @Query("size") size: Int
    ): PageResponse<HistoryDto>

    // 특정 디스펜서 과거 이력  GET  /api/histories/{dispenserId}?page=0&size=20
    @GET("/api/histories/{dispenserId}")
    suspend fun getHistoriesByDispenser(
        @Path("dispenserId") dispenserId: Long,
        @Query("page") page: Int,
        @Query("size") size: Int
    ): PageResponse<HistoryDto>
}

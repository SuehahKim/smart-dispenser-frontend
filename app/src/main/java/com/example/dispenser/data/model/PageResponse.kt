package com.example.dispenser.data.model

data class PageResponse<T>(
    val content: List<T>,
    val pageable: Pageable?,           // 필요 없으면 nullable
    val totalPages: Int,
    val totalElements: Long,
    val last: Boolean,
    val size: Int,                     // 요청한 page size
    val number: Int,                   // 현재 page 번호 (0-based)
    val sort: Sort?,
    val numberOfElements: Int,
    val first: Boolean,
    val empty: Boolean
)

data class Pageable(
    val pageNumber: Int,
    val pageSize: Int,
    val offset: Long,
    val paged: Boolean,
    val unpaged: Boolean,
    val sort: Sort?
)

data class Sort(
    val empty: Boolean,
    val sorted: Boolean,
    val unsorted: Boolean
)
package com.example.dispenser.data.model

// 재료 이름 한 개
data class IngredientName(
    val name: String = ""
)

// 사용 이력 한 건
data class HistoryDto(
    val historyId: Long,
    val status: String,
    val requestedAt: String,
    val completedAt: String?,
    val userId: Long,
    val dispenserId: Long,
    val recipeId: Long,

    // ✅ 백엔드가 새로 내려주는 필드
    val recipeName: String = "",
    val ingredientsNames: List<IngredientName> = emptyList(),
    val favorite: Boolean = false
)

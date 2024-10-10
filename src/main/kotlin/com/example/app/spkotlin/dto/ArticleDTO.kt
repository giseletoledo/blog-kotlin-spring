package com.example.app.spkotlin.dto

import java.time.LocalDateTime

data class ArticleDTO(
    val title: String?,
    val content: String,
    val slug: String?,
    val createdAt: LocalDateTime = LocalDateTime.now()
)

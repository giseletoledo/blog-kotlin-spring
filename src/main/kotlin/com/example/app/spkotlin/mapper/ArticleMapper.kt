package com.example.app.spkotlin.mapper

import com.example.app.spkotlin.dto.ArticleDTO
import com.example.app.spkotlin.model.Article

// Função de extensão para converter Article em ArticleDTO
fun Article.toDTO(): ArticleDTO {
    return ArticleDTO(
        title = this.title,
        content = this.content,
        slug = this.slug,
        createdAt = this.createdAt
    )
}

// Função de extensão para converter ArticleDTO em Article
fun ArticleDTO.toEntity(): Article {
    return Article(
        title = this.title,
        content = this.content,
        slug = this.title?.toSlug()  // Certifique-se de ter a função toSlug implementada
    )
}

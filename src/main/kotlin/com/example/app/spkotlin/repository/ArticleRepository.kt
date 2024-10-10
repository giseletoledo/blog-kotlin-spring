package com.example.app.spkotlin.repository


import com.example.app.spkotlin.model.Article
import org.springframework.data.jpa.repository.JpaRepository


interface ArticleRepository : JpaRepository<Article, Long> {
    fun findAllByOrderByCreatedAtDesc(): Iterable<Article>
    fun findBySlug(slug: String): Article?
    fun findByTitle(title: String): Article?
}

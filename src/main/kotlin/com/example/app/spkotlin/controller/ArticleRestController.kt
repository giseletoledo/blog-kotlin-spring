package com.example.app.spkotlin.controller

import com.example.app.spkotlin.dto.ArticleDTO
import com.example.app.spkotlin.service.ArticleService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/articles")
class ArticleRestController(private val articleService: ArticleService<Any?>) {

    @GetMapping
    fun getAllArticles(): List<ArticleDTO> {
        return articleService.getAllArticles()
    }

    @GetMapping("/recents")
    fun getRecentArticles(): List<ArticleDTO> {
        return articleService.getRecentArticles()
    }

    @GetMapping("/{id}")
    fun getArticleById(@PathVariable id: Long): ArticleDTO {
        return articleService.getArticleById(id)
    }

    @GetMapping("/slug/{slug}")
    fun getArticleBySlug(@PathVariable slug: String): ArticleDTO {
        return articleService.getArticleBySlug(slug)
    }

    @PostMapping
    fun createArticle(@RequestBody articleDTO: ArticleDTO): ArticleDTO {
        return articleService.createArticle(articleDTO)
    }

    @PutMapping("/{id}")
    fun updateArticle(@PathVariable id: Long, @RequestBody updatedArticleDTO: ArticleDTO): ArticleDTO {
        return articleService.updateArticle(id, updatedArticleDTO)
    }

    @DeleteMapping("/{id}")
    fun deleteArticle(@PathVariable id: Long) {
        articleService.deleteArticle(id)
    }
}

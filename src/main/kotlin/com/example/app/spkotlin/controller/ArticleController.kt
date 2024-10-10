package com.example.app.spkotlin.controller

import com.example.app.spkotlin.model.Article
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException

@RestController
@RequestMapping("/api/v1/articles")
class ArticleController {

    val articles = mutableListOf(Article(title = "Meu Título", content ="conteúdo"))

    @GetMapping
    fun articles(): MutableList<Article> {
        return articles
    }

    @GetMapping("/{slug}")
    fun articles(@PathVariable slug: String): Article =
        articles.find {
            article -> article.slug == slug } ?: throw ResponseStatusException(HttpStatus.NOT_FOUND)

    @PostMapping
    fun newArticle(@RequestBody article: Article): Article {
        articles.add(article)
        return article
    }

    @PutMapping("/{title}")
    fun updateArticle(@RequestBody article: Article, @PathVariable title: String): Article {
        val existingArticle = articles.find { it.title == title } ?: throw  ResponseStatusException(HttpStatus.NOT_FOUND)
        existingArticle.content = article.content;
        return article
    }

}
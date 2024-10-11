package com.example.app.spkotlin.controller

import com.example.app.spkotlin.model.Article
import com.example.app.spkotlin.service.ArticleService
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import java.time.format.DateTimeFormatter


@Controller
@RequestMapping("/articles")
class ArticleThymeleafController(private val articleService: ArticleService<Any?>) {

    @GetMapping("/view")
    fun getAllArticles(model: Model): String {
        val articles = articleService.getAllArticles()
        model.addAttribute("articles", articles)
        return "article-list" // Nome do arquivo Thymeleaf (sem extensão)
    }

    @GetMapping("/view/recents")
    fun getRecentArticles(model: Model): String {
        val articles = articleService.getRecentArticles()
        model.addAttribute("articles", articles)
        return "recent-articles" // Nome do arquivo HTML dentro da pasta templates
    }

    @GetMapping("/search")
    fun searchArticles(@RequestParam title: String, model: Model): String {
        val articles = if (title.isBlank()) {
            // Se o campo de título estiver vazio, retornar uma lista vazia
            emptyList() // Retorna uma lista vazia para representar que não há artigos
        } else {
            // Caso contrário, realizar a busca
            articleService.searchByTitle(title)
        }

        // Formatação da data
        val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")
        articles.forEach { article ->
            article.formattedCreatedAt = article.createdAt.format(formatter) // Supondo que você tenha um setter para isso
        }

        model.addAttribute("articles", articles)
        model.addAttribute("searchTitle", title) // Adiciona o título ao modelo

        // Adiciona uma flag para indicar se não há artigos
        model.addAttribute("noArticles", articles.isEmpty())

        return "article-list" // O nome do template Thymeleaf
    }



}
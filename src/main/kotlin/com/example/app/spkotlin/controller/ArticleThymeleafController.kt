package com.example.app.spkotlin.controller

import com.example.app.spkotlin.dto.ArticleDTO
import com.example.app.spkotlin.model.Article
import com.example.app.spkotlin.service.ArticleService
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.mvc.support.RedirectAttributes
import java.time.format.DateTimeFormatter

import org.owasp.html.PolicyFactory
import org.owasp.html.Sanitizers

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
        // Valida se o título está vazio ou possui menos de 4 caracteres
        if (title.isBlank()) {
            model.addAttribute("errorMessage", "Por favor, insira uma palavra para buscar.")
            model.addAttribute("articles", emptyList<Article>())
            return "article-list" // O nome do template Thymeleaf
        } else if (title.length < 4) {
            model.addAttribute("errorMessage", "O termo de busca deve ter pelo menos 4 caracteres.")
            model.addAttribute("articles", emptyList<Article>())
            return "article-list" // O nome do template Thymeleaf
        }

        // Realiza a busca
        val articles = articleService.searchByTitle(title)

        // Formatação da data
        val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")
        articles.forEach { article ->
            article.formattedCreatedAt = article.createdAt.format(formatter) // Supondo que você tenha um setter para isso
        }

        // Adiciona atributos ao modelo
        model.addAttribute("articles", articles)
        model.addAttribute("searchTitle", title) // Adiciona o título ao modelo

        // Adiciona uma flag para indicar se não há artigos
        if (articles.isEmpty()) {
            model.addAttribute("noArticles", true)
            model.addAttribute("infoMessage", "Nenhum artigo encontrado para \"$title\".")
        } else {
            model.addAttribute("noArticles", false)
        }

        return "article-list" // O nome do template Thymeleaf
    }


    // Adicionando metodo para renderizar o formulário de criação
    @GetMapping("/view/create")
    fun showCreateForm(model: Model): String {
        model.addAttribute("article", ArticleDTO(null, "", null))
        return "create-article"
    }

    @PostMapping("/view/create")
    fun createArticle(@ModelAttribute articleDTO: ArticleDTO, redirectAttributes: RedirectAttributes): String {
        // Sanitiza o conteúdo do artigo usando OWASP sanitizer
        val policy: PolicyFactory = Sanitizers.FORMATTING.and(Sanitizers.BLOCKS).and(Sanitizers.LINKS)
        val sanitizedContent = policy.sanitize(articleDTO.content)

        // Atualiza o artigo com o conteúdo sanitizado
        val sanitizedArticleDTO = articleDTO.copy(content = sanitizedContent)

        // Chama o serviço para salvar o artigo
        articleService.createArticle(sanitizedArticleDTO)

        // Mensagem de sucesso para o usuário
        redirectAttributes.addFlashAttribute("successMessage", "Article created successfully!")

        return "redirect:/articles/view"
    }

}
package com.example.app.spkotlin.service

import com.example.app.spkotlin.dto.ArticleDTO
import com.example.app.spkotlin.extensions.toSlug
import com.example.app.spkotlin.mapper.toDTO
import com.example.app.spkotlin.mapper.toEntity
import com.example.app.spkotlin.repository.ArticleRepository
import org.springframework.stereotype.Service

@Service
class ArticleService<Article>(private val articleRepository: ArticleRepository) {

    fun getAllArticles(): List<ArticleDTO> {
        return articleRepository.findAll().map { it.toDTO() }
    }

    fun getRecentArticles(): List<ArticleDTO> {
        return articleRepository.findAllByOrderByCreatedAtDesc().map { it.toDTO() }
    }

    fun getArticleById(id: Long): ArticleDTO {
        val article = articleRepository.findById(id).orElseThrow {
            throw IllegalArgumentException("Artigo não encontrado com ID: $id")
        }
        return article.toDTO()
    }

    fun getArticleBySlug(slug: String): ArticleDTO {
        val article = articleRepository.findBySlug(slug)
            ?: throw IllegalArgumentException("Artigo não encontrado com slug: $slug")
        return article.toDTO()
    }

    fun createArticle(articleDTO: ArticleDTO): ArticleDTO {
        // Converte o DTO em uma entidade
        val article = articleDTO.toEntity()

        // Salva a entidade no banco de dados
        val savedArticle = articleRepository.save(article)

        // Retorna o DTO do artigo salvo
        return savedArticle.toDTO()
    }

    fun updateArticle(id: Long, updatedArticleDTO: ArticleDTO): ArticleDTO {
        val existingArticle = articleRepository.findById(id).orElseThrow {
            throw IllegalArgumentException("Artigo não encontrado com ID: $id")
        }

        existingArticle.title = updatedArticleDTO.title ?: existingArticle.title
        existingArticle.content = updatedArticleDTO.content
        existingArticle.slug = updatedArticleDTO.title?.toSlug() ?: existingArticle.slug

        return articleRepository.save(existingArticle).toDTO()
    }

    fun deleteArticle(id: Long) {
        val existingArticle = articleRepository.findById(id).orElseThrow {
            throw IllegalArgumentException("Artigo não encontrado com ID: $id")
        }
        articleRepository.delete(existingArticle)
    }

    fun searchByTitle(title: String): List<com.example.app.spkotlin.model.Article> {
        if (title.length < 4) {
            // Handle short search term (e.g., throw an exception, return an empty list)
            throw IllegalArgumentException("Search term must be at least 4 characters long")
        }

        return articleRepository.findByTitleContainingIgnoreCase(title)
    }
}

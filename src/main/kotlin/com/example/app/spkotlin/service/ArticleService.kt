package com.example.app.spkotlin.service

import com.example.app.spkotlin.dto.ArticleDTO
import com.example.app.spkotlin.extensions.toSlug
import com.example.app.spkotlin.mapper.toDTO
import com.example.app.spkotlin.mapper.toEntity
import com.example.app.spkotlin.repository.ArticleRepository
import org.springframework.stereotype.Service

@Service
class ArticleService(private val articleRepository: ArticleRepository) {

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
        val article = articleDTO.toEntity()
        return articleRepository.save(article).toDTO()
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
}

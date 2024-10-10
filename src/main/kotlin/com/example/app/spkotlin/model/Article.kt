package com.example.app.spkotlin.model

import com.example.app.spkotlin.extensions.toSlug
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import java.time.LocalDateTime

@Entity
class Article(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
    var title: String,
    var content: String,
    val createdAt: LocalDateTime = LocalDateTime.now(),
    var slug: String = title.toSlug()
)
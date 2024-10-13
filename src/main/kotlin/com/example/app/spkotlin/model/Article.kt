package com.example.app.spkotlin.model

import com.example.app.spkotlin.extensions.toSlug
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
class Article(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
    @Column(length = 150)
    var title: String,
    @Column(length = 5000)
    var content: String,
    val createdAt: LocalDateTime = LocalDateTime.now(),
    var slug: String = title.toSlug()
) {
    var formattedCreatedAt: String? = null
}
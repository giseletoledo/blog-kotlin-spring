package com.example.app.spkotlin.extensions

import java.util.*


fun String.toSlug(): String = this
    .map { char ->
        when (char) {
            'á', 'à', 'ã', 'â', 'ä' -> 'a'
            'é', 'è', 'ê', 'ë' -> 'e'
            'í', 'ì', 'î', 'ï' -> 'i'
            'ó', 'ò', 'õ', 'ô', 'ö' -> 'o'
            'ú', 'ù', 'û', 'ü' -> 'u'
            'ç' -> 'c'
            'ñ' -> 'n'
            else -> char
        }
    }.joinToString("")
    .lowercase(Locale.getDefault())
    .replace("\n", "")
    .replace("[^a-z\\d\\s]".toRegex(), "") // Remove caracteres especiais
    .split("\\s+".toRegex()) // Divide por espaços
    .joinToString("-")
    .replace("-+".toRegex(), "-") // Remove múltiplos hifens

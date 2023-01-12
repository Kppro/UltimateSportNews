package com.usn.ultimatesportnews.utils

fun generateText(paragraphs: Int): List<String> {
    val list = mutableListOf<String>()
    for (i in 1..paragraphs) {
        list.add("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed euismod, risus at molestie dictum, ipsum augue blandit tellus, vel fringilla augue magna at risus.")
    }
    return list
}
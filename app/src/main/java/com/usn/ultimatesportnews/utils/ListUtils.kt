package com.usn.ultimatesportnews.utils

fun <T> mix(list1: List<T>, list2: List<T>): List<T> {
    val result = mutableListOf<T>()
    val size = if (list1.size > list2.size) list1.size else list2.size
    for (i in 0 until size) {
        if (i < list1.size) result.add(list1[i])
        if (i < list2.size) result.add(list2[i])
    }
    return result
}

//val list1 = listOf(1, 2, 3)
//val list2 = listOf(4, 5)
//
//val result = mutableListOf<Int>()
//val max = maxOf(list1.size, list2.size)
//
//for (i in 0 until max) {
//    if (i < list1.size) result.add(list1[i])
//    if (i < list2.size) result.add(list2[i])
//}
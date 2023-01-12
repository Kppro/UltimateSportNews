package com.usn.ultimatesportnews.utils

import org.junit.Assert.assertEquals
import org.junit.Test

internal class ListUtilsKtTest {

    @Test
    fun testMix() {
        val list1 = listOf("a", "b", "c")
        val list2 = listOf("1", "2")

        val expected = listOf("a", "1", "b", "2", "c")
        val result = mix(list1, list2)

        assertEquals(expected, result)
    }

    @Test
    fun testMix_withDifferentSizes() {
        val list1 = listOf("a", "b", "c", "d")
        val list2 = listOf("1", "2")

        val expected = listOf("a", "1", "b", "2", "c", "d")
        val result = mix(list1, list2)

        assertEquals(expected, result)
    }

    @Test
    fun testMix_withEmptyList() {
        val list1 = listOf<String>()
        val list2 = listOf("1", "2")

        val expected = listOf("1", "2")
        val result = mix(list1, list2)

        assertEquals(expected, result)
    }
}
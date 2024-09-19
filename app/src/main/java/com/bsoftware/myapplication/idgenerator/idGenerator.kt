package com.bsoftware.myapplication.idgenerator

import kotlin.random.Random

class idGenerator {
    fun generateRandomId(length: Int): String {
        val characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789"
        val randomId = StringBuilder()

        for (i in 0 until length) {
            val randomIndex = Random.nextInt(characters.length)
            randomId.append(characters[randomIndex])
        }

        return randomId.toString()
    }
}
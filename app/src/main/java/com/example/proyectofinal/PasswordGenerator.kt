package com.example.proyectofinal

import java.lang.StringBuilder

class PasswordGenerator {
    val characters = "ABC123456789"
    fun generatePassword(length:Int,specialWord: String=""): String {
        val sb = StringBuilder(length)

        for ( x in 0 until length){
            val random = (characters.indices).random()
            sb.append(characters[random])
        }
        sb.insert((0 until length).random(), specialWord)
        return sb.toString()

    }
}
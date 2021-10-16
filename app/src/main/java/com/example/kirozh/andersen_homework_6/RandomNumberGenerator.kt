package com.example.kirozh.andersen_homework_6

/**
 * @author Kirill Ozhigin on 16.10.2021
 */
class RandomNumberGenerator {
    fun randomNumberGenerate(num: Int): MutableList<String> {
        val numbers = mutableListOf<String>()
        val sb = StringBuilder()
        for (i in 0 until num) {
            sb.append("+")
            sb.append((1..9).random())
            sb.append("-(")
            sb.append((1..9).random())
            sb.append((1..9).random())
            sb.append((1..9).random())
            sb.append(")-")
            sb.append((0..9).random())
            sb.append((0..9).random())
            sb.append((0..9).random())
            sb.append("-")
            sb.append((0..9).random())
            sb.append((0..9).random())
            sb.append("-")
            sb.append((0..9).random())
            sb.append((0..9).random())

            numbers.add(sb.toString())
            sb.clear()
        }

        return numbers
    }
}
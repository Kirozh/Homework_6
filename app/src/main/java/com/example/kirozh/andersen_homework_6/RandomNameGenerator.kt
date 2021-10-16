package com.example.kirozh.andersen_homework_6

/**
 * @author Kirill Ozhigin on 16.10.2021
 */
class RandomNameGenerator {

    private val widespreadNames = mutableListOf(
        "Marc", "Peter", "Alex", "John", "Andrew", "Mary",
        "Sophie", "Anna", "Victory", "Katharina", "Lucas",
        "David", "Philip", "George", "Jack", "James", "Emma",
        "Adam", "Lucia", "Olivia", "Eva"
    )

    private val widespreadSurnames = mutableListOf(
        "Jones", "Johnson", "Li", "Song",
        "Garcia", "Martinez", "Smith", "Brown", "Martin",
        "Muller", "Davis", "Taylor", "Anderson", "Edwards",
        "Walker", "McAwn", "Nelson", "Roberts"
    )

    fun randomNamesGenerate(num: Int): MutableList<Pair<String, String>> {
        val names = mutableListOf<Pair<String, String>>()
        for (i in 0 until num) {
            val randomName = widespreadNames[(0 until widespreadNames.size).random()]
            val randomSurname = widespreadSurnames[(0 until widespreadSurnames.size).random()]
            val pair = Pair(randomName, randomSurname)
            names.add(pair)
        }
        return names
    }
}
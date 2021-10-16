package com.example.kirozh.andersen_homework_6

/**
 * @author Kirill Ozhigin on 15.10.2021
 */

const val contactNum = 110

class ContactList {

    companion object {
        var contacts = mutableListOf<Contact>()

        private val names = RandomNameGenerator().randomNamesGenerate(contactNum)
        private val phones = RandomNumberGenerator().randomNumberGenerate(contactNum)

        init {
            for (i in 0 until contactNum) {
                var contact = Contact(
                    i,
                    names[i].first,
                    names[i].second,
                    phones[i],
                    "https://loremflickr.com/240/240?lock=${i + 1}"
                )
                contacts.add(i, contact)
            }
        }
    }

}
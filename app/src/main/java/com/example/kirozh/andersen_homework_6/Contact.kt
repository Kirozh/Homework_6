package com.example.kirozh.andersen_homework_6

/**
 * @author Kirill Ozhigin on 15.10.2021
 */
data class Contact(
    var contactId: Int = 0,
    var contactName: String = "",
    var contactSurname: String = "",
    var contactPhone: String = "",
    var contactImageURL: String = "https://loremflickr.com/320/240"
)
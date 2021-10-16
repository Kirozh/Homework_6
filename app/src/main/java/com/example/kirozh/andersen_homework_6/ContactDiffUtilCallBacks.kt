package com.example.kirozh.andersen_homework_6

import androidx.recyclerview.widget.DiffUtil

/**
 * @author Kirill Ozhigin on 17.10.2021
 */
class ContactDiffUtilCallBacks(
    private val oldContactList: MutableList<Contact>,
    private val newContactList: MutableList<Contact>
) : DiffUtil.Callback() {


    override fun getOldListSize(): Int {
        return oldContactList.size
    }

    override fun getNewListSize(): Int {
        return newContactList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldContactList[oldItemPosition].contactId == newContactList[newItemPosition].contactId
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return when {
            oldContactList[oldItemPosition].contactId != newContactList[newItemPosition].contactId -> {
                false
            }
            oldContactList[oldItemPosition].contactName != newContactList[newItemPosition].contactName -> {
                false
            }
            oldContactList[oldItemPosition].contactSurname != newContactList[newItemPosition].contactSurname -> {
                false
            }
            oldContactList[oldItemPosition].contactPhone != newContactList[newItemPosition].contactPhone -> {
                false
            }
            else -> true
        }

    }
}
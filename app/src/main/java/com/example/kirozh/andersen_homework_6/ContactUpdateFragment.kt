package com.example.kirozh.andersen_homework_6

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso

/**
 * @author Kirill Ozhigin on 15.10.2021
 */

const val EDIT_CONTACT_ID = "contact_id"

class ContactUpdateFragment : Fragment(R.layout.fragment_contact_update) {

    private lateinit var nameET: EditText
    private lateinit var surnameET: EditText
    private lateinit var numberET: EditText
    private lateinit var updateBtn: Button
    private lateinit var contactPhotoIV: ImageView

    private lateinit var toolbar: Toolbar

    private var updateCallBacks: UpdateCallBacks? = null

    private var contactId: Int = 0
    var contacts = ContactList.contacts

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if (context is UpdateCallBacks)
            updateCallBacks = context
        else
            throw IllegalArgumentException("not implemented to activity")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        contactId = arguments?.getInt(EDIT_CONTACT_ID) as Int

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        nameET = view.findViewById(R.id.nameEditText)
        surnameET = view.findViewById(R.id.surnameEditText)
        numberET = view.findViewById(R.id.numberEditText)
        updateBtn = view.findViewById(R.id.update_btn)
        contactPhotoIV = view.findViewById(R.id.fragment_update_image_view)

        nameET.setText(contacts[contactId].contactName)
        surnameET.setText(contacts[contactId].contactSurname)
        numberET.setText(contacts[contactId].contactPhone)

        toolbar = view.findViewById(R.id.update_toolbar)
        (activity as AppCompatActivity).setSupportActionBar(toolbar)
        (activity as AppCompatActivity).supportActionBar?.setDisplayShowHomeEnabled(true)
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)

        toolbar.setNavigationOnClickListener {
            onBackClicked(contactId)
        }

        updateBtn.setOnClickListener {
            contacts[contactId].apply {
                contactName = nameET.text.toString()
                contactSurname = surnameET.text.toString()
                contactPhone = numberET.text.toString()
            }

            updateCallBacks?.onUpdateContactClickListener(contactId)
        }

        contactImageDownload(contactPhotoIV)
    }

    private fun onBackClicked(id: Int) {
        updateCallBacks?.onBackToDetailContactClickListener(id)
    }

    private fun contactImageDownload(iv: ImageView) {
        val uri = Uri.parse(contacts[contactId].contactImageURL)
        val picassoBuilder = context?.let { Picasso.Builder(it) }
        picassoBuilder?.listener { _, _, exception -> exception.printStackTrace() }

        picassoBuilder
            ?.build()
            ?.load(uri)
            ?.into(iv, object : Callback {
                override fun onSuccess() {
                }

                override fun onError(e: Exception?) {
                    Toast.makeText(
                        activity,
                        "Cannot download",
                        Toast.LENGTH_LONG
                    ).show()
                }
            })
    }

    override fun onDetach() {
        super.onDetach()
        updateCallBacks = null
    }

    interface UpdateCallBacks {
        fun onUpdateContactClickListener(contact_id: Int)
        fun onBackToDetailContactClickListener(contact_id: Int)
    }

    companion object {
        fun newInstance(contact_id: Int): ContactUpdateFragment {
            val args = Bundle().apply {
                putInt(EDIT_CONTACT_ID, contact_id)
            }
            return ContactUpdateFragment().apply {
                arguments = args
            }
        }
    }
}
package com.example.kirozh.andersen_homework_6

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso

/**
 * @author Kirill Ozhigin on 15.10.2021
 */

const val DETAIL_CONTACT_ID = "contact_id"

class ContactDetailFragment : Fragment(R.layout.fragment_contact_detail) {

    private lateinit var nameTV: TextView
    private lateinit var surnameTV: TextView
    private lateinit var phoneTV: TextView
    private lateinit var photoIV: ImageView

    private lateinit var toolbar: Toolbar
    private lateinit var fab: FloatingActionButton

    private var detailCallBacks: DetailCallBacks? = null

    private var contactId: Int = 0

    private var contacts = ContactList.contacts

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if (context is DetailCallBacks)
            detailCallBacks = context
        else
            throw IllegalArgumentException("Host activity must implement methods")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)

        contactId = arguments?.getInt(DETAIL_CONTACT_ID) as Int

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        nameTV = view.findViewById(R.id.fragment_detail_name)
        surnameTV = view.findViewById(R.id.fragment_detail_surname)
        phoneTV = view.findViewById(R.id.fragment_detail_number)
        photoIV = view.findViewById(R.id.fragment_detail_image_view)

        fab = view.findViewById(R.id.floatingActionButton)

        toolbar = view.findViewById(R.id.detail_toolbar)
        (activity as AppCompatActivity).setSupportActionBar(toolbar)
        (activity as AppCompatActivity).supportActionBar?.setDisplayShowHomeEnabled(true)
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)

        toolbar.setNavigationOnClickListener {
            onBackClicked()
        }

        fab.setOnClickListener {
            detailCallBacks?.onEditContactClickListener(contactId)
        }

        nameTV.text = contacts[contactId].contactName
        surnameTV.text = contacts[contactId].contactSurname
        phoneTV.text = contacts[contactId].contactPhone
        contactImageDownload(photoIV)

    }

    private fun onBackClicked() {
        detailCallBacks?.onBackToContactListClickListener()
    }

    private fun contactImageDownload(iv: ImageView) {
        val uri = Uri.parse(contacts[contactId].contactImageURL)
        val picassoBuilder = context?.let { Picasso.Builder(it) }
        picassoBuilder?.listener { _, _, exception -> exception.printStackTrace() }

        picassoBuilder
            ?.build()
            ?.load(uri)
            ?.resize(400, 400)
            ?.centerCrop()
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
        detailCallBacks = null
    }

    interface DetailCallBacks {
        fun onEditContactClickListener(id: Int)
        fun onBackToContactListClickListener()
    }

    companion object {

        fun newInstance(contact_id: Int): ContactDetailFragment {
            val args = Bundle().apply {
                putInt(DETAIL_CONTACT_ID, contact_id)
            }
            return ContactDetailFragment().apply {
                arguments = args
            }
        }
    }
}
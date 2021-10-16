package com.example.kirozh.andersen_homework_6

import android.annotation.SuppressLint
import android.content.ClipData
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import jp.wasabeef.picasso.transformations.CropCircleTransformation

/**
 * @author Kirill Ozhigin on 16.10.2021
 */
class ContactAdapter(private val onClick: (Contact, Int) -> Unit) :
    RecyclerView.Adapter<ContactAdapter.ContactHolder>(), Filterable {

    var contacts = ContactList.contacts
    var contactFilterList = mutableListOf<Contact>()

    init {
        contactFilterList = contacts
    }

    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ) = ContactHolder(
        LayoutInflater.from(parent.context).inflate(
            R.layout.item_view,
            parent,
            false
        )
    )

    @SuppressLint("ResourceAsColor")
    override fun onBindViewHolder(holder: ContactHolder, position: Int) {
        val contact: Contact = contactFilterList[position]
        holder.bind(contact)
        holder.itemView.setOnClickListener { onClick(contact, position) }
    }

    override fun getItemCount(): Int = contactFilterList.size

    fun setData(newContactList: MutableList<Contact>){
        val diffUtil = ContactDiffUtilCallBacks(contactFilterList, newContactList)
        val diffResults = DiffUtil.calculateDiff(diffUtil,true)
        contactFilterList = newContactList
        diffResults.dispatchUpdatesTo(this)
    }
    fun removeItem(position: Int) {
        contactFilterList.removeAt(position)
        notifyDataSetChanged()
    }

    inner class ContactHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val nameTV: TextView = itemView.findViewById(R.id.nameTextView)
        private val surnameTV: TextView = itemView.findViewById(R.id.surnameTextView)
        private val image: ImageView = itemView.findViewById(R.id.imageView)
        private val phoneTV: TextView = itemView.findViewById(R.id.phoneTextView)
        private val deleteIV: ImageView = itemView.findViewById(R.id.deleteImageView)

        private lateinit var contact: Contact

        fun bind(contact: Contact) {
            this.contact = contact
            nameTV.text = this.contact.contactName
            surnameTV.text = this.contact.contactSurname
            phoneTV.text = this.contact.contactPhone
            val uri = (Uri.parse(this.contact.contactImageURL))
            Picasso.get().load(uri).transform(CropCircleTransformation()).into(image)

            deleteIV.setOnClickListener {
                removeItem(bindingAdapterPosition)
            }
        }
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {

                val charSearch = constraint.toString()
                contactFilterList = if (charSearch.isEmpty()) {
                    contacts
                } else {
                    val resultList = mutableListOf<Contact>()

                    for (row in contacts) {
                        if (row.contactName.lowercase().contains(charSearch.lowercase()) ||
                            row.contactSurname.lowercase().contains(charSearch.lowercase())
                        )
                            resultList.add(row)
                    }
                    resultList
                }

                val filterResult = FilterResults()
                filterResult.values = contactFilterList
                return filterResult
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                contactFilterList = results?.values as MutableList<Contact>
                notifyDataSetChanged()
            }
        }
    }
}
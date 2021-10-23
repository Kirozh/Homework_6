package com.example.kirozh.andersen_homework_6

import android.annotation.SuppressLint
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import jp.wasabeef.picasso.transformations.CropCircleTransformation

/**
 * @author Kirill Ozhigin on 16.10.2021
 */
class ContactAdapter(clickListener: ItemClickListener) :
    RecyclerView.Adapter<ContactAdapter.ContactHolder>(), Filterable {

    var mItemClickListener: ItemClickListener = clickListener

    var contactFilteredList = mutableListOf<Contact>()
    var contacts = mutableListOf<Contact>()

    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ) = ContactHolder(
        LayoutInflater.from(parent.context).inflate(
            R.layout.item_view,
            parent,
            false
        ), mItemClickListener
    )

    @SuppressLint("ResourceAsColor")
    override fun onBindViewHolder(holder: ContactHolder, position: Int) {
        Log.d("TAG", "bind, position = $position")
        val contact: Contact = contactFilteredList[position]
        holder.bind(contact, position)
        holder.itemView.setOnClickListener { mItemClickListener.onItemClick(contact, position) }
    }

    override fun getItemCount(): Int = contacts.size

    fun setData(newContactList: MutableList<Contact>) {
        val diffUtil = ContactDiffUtilCallBacks(contactFilteredList, newContactList)
        val diffResults = DiffUtil.calculateDiff(diffUtil, true)
        contactFilteredList = newContactList
        contacts = contactFilteredList
        diffResults.dispatchUpdatesTo(this)
    }

    class ContactHolder(view: View, itemClickListener: ItemClickListener) :
        RecyclerView.ViewHolder(view) {

        private val nameTV: TextView = itemView.findViewById(R.id.nameTextView)
        private val surnameTV: TextView = itemView.findViewById(R.id.surnameTextView)
        private val image: ImageView = itemView.findViewById(R.id.imageView)
        private val phoneTV: TextView = itemView.findViewById(R.id.phoneTextView)
        private val deleteIV: ImageView = itemView.findViewById(R.id.deleteImageView)

        private lateinit var contact: Contact

        private var mItemClickListener: ItemClickListener = itemClickListener

        fun bind(contact: Contact, pos: Int) {
            this.contact = contact
            nameTV.text = this.contact.contactName
            surnameTV.text = this.contact.contactSurname
            phoneTV.text = this.contact.contactPhone

            val uri = (Uri.parse(this.contact.contactImageURL))
            Picasso.get().load(uri).transform(CropCircleTransformation()).into(image)

            deleteIV.setOnClickListener {
                mItemClickListener.onDeleteClicked(pos)
            }
        }
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {

                val charSearch = constraint.toString()
                contactFilteredList = if (charSearch.isEmpty()) {
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
                filterResult.values = contactFilteredList
                return filterResult
            }

            @SuppressLint("NotifyDataSetChanged")
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                contactFilteredList = results?.values as MutableList<Contact>
                notifyDataSetChanged()
            }
        }
    }

    interface ItemClickListener {
        fun onDeleteClicked(position: Int)
        fun onItemClick(contact: Contact, position: Int)
    }
}
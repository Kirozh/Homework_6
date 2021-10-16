package com.example.kirozh.andersen_homework_6

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

/**
 * @author Kirill Ozhigin on 15.10.2021
 */

class ContactListFragment : Fragment() {
    private var contacts = ContactList.contacts
    private lateinit var recyclerView: RecyclerView
    private var adapter: ContactAdapter? = null

    private lateinit var searchView: SearchView

    private var callBacks: CallBacks? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if (context is CallBacks)
            callBacks = context
        else
            throw IllegalArgumentException("Host activity must implement methods")

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_contact_list, container, false)

        recyclerView = view.findViewById(R.id.contact_recycler_view) as RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(context)

        adapter = ContactAdapter { _, id -> callBacks?.onItemClickListener(id) }
        adapter!!.setData(contacts)
        recyclerView.adapter = adapter

        searchView = view.findViewById(R.id.search_view)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                adapter!!.filter.filter(newText)
                return false
            }

        })

        val dividerItemDecoration = DividerItemDecoration(context, RecyclerView.VERTICAL)
        activity?.let { it ->
            ContextCompat.getDrawable(it, R.drawable.divider_drawable)?.let {
                dividerItemDecoration.setDrawable(
                    it
                )
            }
        }

        recyclerView.addItemDecoration(dividerItemDecoration)

        return view
    }

    override fun onDetach() {
        super.onDetach()
        callBacks = null
    }

    interface CallBacks {
        fun onItemClickListener(id: Int)
    }

    companion object {
        fun newInstance(): ContactListFragment {
            return ContactListFragment()
        }
    }

}
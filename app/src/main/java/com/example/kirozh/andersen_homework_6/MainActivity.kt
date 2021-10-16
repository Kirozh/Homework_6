package com.example.kirozh.andersen_homework_6

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity(),
    ContactDetailFragment.DetailCallBacks,
    ContactUpdateFragment.UpdateCallBacks,
    ContactListFragment.CallBacks {

    private var phoneConfig = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        createListFragment(R.id.fragment_container)
    }

    override fun onItemClickListener(id: Int) {
        Log.d("detail", "$id")
        supportFragmentManager.beginTransaction().run {
            val detailFragment = ContactDetailFragment.newInstance(id)
            replace(R.id.fragment_container, detailFragment)
            addToBackStack("detail")
            commit()
        }
    }

    override fun onEditContactClickListener(id: Int) {

        supportFragmentManager.beginTransaction().run {
            val editFragment = ContactUpdateFragment.newInstance(id)
            if (phoneConfig)
                replace(R.id.fragment_container, editFragment)
            else
                replace(R.id.right_fragment_container, editFragment)
            addToBackStack("update")
            commit()
        }
    }

    override fun onBackToContactListClickListener() {
        createListFragment(R.id.fragment_container)
    }

    override fun onUpdateContactClickListener(id: Int) {
        if (phoneConfig) {
            createListFragment(R.id.fragment_container)
        } else {
            createListFragment(R.id.left_fragment_container)
            createDetailFragment(R.id.right_fragment_container, id)
        }
    }

    override fun onBackToDetailContactClickListener(id: Int) {
        createDetailFragment(R.id.fragment_container, id)
    }

    override fun onBackPressed() {
        if (phoneConfig) {
            if (supportFragmentManager.getBackStackEntryAt(supportFragmentManager.backStackEntryCount - 1)
                    .name.toString() == "list"
            )
                this.finish()
            else
                supportFragmentManager.popBackStackImmediate()
        } else {
            if (supportFragmentManager
                    .getBackStackEntryAt(supportFragmentManager.backStackEntryCount - 1)
                    .name.toString() == "detail"
            )
                this.finish()
            else
                supportFragmentManager.popBackStackImmediate()

        }
    }

    private fun createDetailFragment(layoutId: Int, contactId: Int) {
        supportFragmentManager.beginTransaction().run {
            val detailFragment = ContactDetailFragment.newInstance(contactId)
            replace(layoutId, detailFragment)
            addToBackStack("detail")
            commit()
        }
    }

    private fun createListFragment(layoutId: Int) {
        supportFragmentManager.beginTransaction().run {
            val updatedListFragment = ContactListFragment.newInstance()
            replace(layoutId, updatedListFragment)
            addToBackStack("list")
            commit()
        }
    }
}
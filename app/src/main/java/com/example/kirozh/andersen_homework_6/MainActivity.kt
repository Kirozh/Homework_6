package com.example.kirozh.andersen_homework_6

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity(),
    ContactDetailFragment.DetailCallBacks,
    ContactUpdateFragment.UpdateCallBacks,
    ContactListFragment.CallBacks {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        createListFragment(R.id.fragment_container)
    }

    override fun onItemClickListener(id: Int) {
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
            replace(R.id.fragment_container, editFragment)
            addToBackStack("update")
            commit()
        }
    }

    override fun onBackToContactListClickListener() {
        createListFragment(R.id.fragment_container)
    }

    override fun onUpdateContactClickListener(contact_id: Int) {
        createListFragment(R.id.fragment_container)
    }

    override fun onBackToDetailContactClickListener(contact_id: Int) {
        createDetailFragment(R.id.fragment_container, contact_id)
    }

    override fun onBackPressed() {
        if (supportFragmentManager.getBackStackEntryAt(
                supportFragmentManager.backStackEntryCount - 1
            ).name.toString() == "list"
        )
            this.finish()
        else
            supportFragmentManager.popBackStackImmediate()
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
package com.example.kirozh.andersen_homework_6

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity(),
    ContactDetailFragment.DetailCallBacks,
    ContactUpdateFragment.UpdateCallBacks,
    ContactListFragment.CallBacks {

    private var isPhoneConfig = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        isPhoneConfig = resources.getBoolean(R.bool.isPhone)
        if (isPhoneConfig)
            createListFragment(R.id.fragment_container)
        else {
            createListFragment(R.id.left_fragment_container)
            createDetailFragment(R.id.right_fragment_container, 0)
        }
    }

    override fun onItemClickListener(id: Int) {
        supportFragmentManager.beginTransaction().run {
            val detailFragment = ContactDetailFragment.newInstance(id)
            if (isPhoneConfig)
                replace(R.id.fragment_container, detailFragment)
            else
                replace(R.id.right_fragment_container, detailFragment)
            addToBackStack("detail")
            commit()
        }
    }

    override fun onEditContactClickListener(id: Int) {

        supportFragmentManager.beginTransaction().run {
            val editFragment = ContactUpdateFragment.newInstance(id)
            if (isPhoneConfig)
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

    override fun onUpdateContactClickListener(contact_id: Int) {
        if (isPhoneConfig) {
            createListFragment(R.id.fragment_container)
        } else {
            createListFragment(R.id.left_fragment_container)
            createDetailFragment(R.id.right_fragment_container, contact_id)
        }
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
package com.example.movieapp.ui.contacts

import android.os.Bundle
import android.provider.ContactsContract
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.movieapp.R
import com.example.movieapp.databinding.FragmentContactsBinding
import com.example.movieapp.domain.model.Contact

class ContactsFragment : Fragment(R.layout.fragment_contacts) {

    private lateinit var viewBinding: FragmentContactsBinding


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding = FragmentContactsBinding.bind(view)
        initContacts()

    }

    private fun initContacts() {
        val contentResolver = requireContext().contentResolver
        val cursor = contentResolver.query(
            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
            null,
            null,
            null,
            ContactsContract.Contacts.DISPLAY_NAME + " DESC"
        )
        val contacts = mutableListOf<Contact>()
        val safeCursor = cursor ?: return
        while (safeCursor.moveToNext()) {
            val number =
                safeCursor.getString(safeCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
            val displayName =
                safeCursor.getString(safeCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME))
            contacts.add(Contact(displayName, number))
        }
        safeCursor.close()
        cursor.close()
        val adapter = ContactsAdapter(contacts)
        viewBinding.contactsRv.adapter = adapter
        viewBinding.contactsRv.layoutManager =
            LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
    }

}
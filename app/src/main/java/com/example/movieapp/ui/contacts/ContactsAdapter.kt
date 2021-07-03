package com.example.movieapp.ui.contacts

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.movieapp.R
import com.example.movieapp.domain.model.Contact

class ContactsAdapter(val data:List<Contact>) : RecyclerView.Adapter<ContactsAdapter.ContactViewHolder>()
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        return ContactViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_contact, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        holder.contactName.text = data[position].displayName
        holder.phoneNumber.text = data[position].phoneNum
    }

    override fun getItemCount(): Int {
        return data.size
    }

    inner class ContactViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val contactName: TextView = itemView.findViewById(R.id.contact_name)
        val phoneNumber: TextView = itemView.findViewById(R.id.phone_number)
    }
}

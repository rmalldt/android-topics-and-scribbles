package com.rm.android_fundamentals.topics.t8_storagetypes.contentprovider

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rm.android_fundamentals.databinding.RecyclerviewItemBinding

class ContactAdapter : RecyclerView.Adapter<ContactAdapter.ContactsViewHolder>() {

    var contactList: List<Contact> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactsViewHolder {
        val binding = RecyclerviewItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ContactsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ContactsViewHolder, position: Int) {
        val item = contactList[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int = contactList.size

    class ContactsViewHolder(val binding: RecyclerviewItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(contact: Contact) {
            binding.txtRvItem.text = contact.name
            binding.txtRvNum.text = contact.number
        }
    }
}
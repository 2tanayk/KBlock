package com.kamathtanay.kblock.util.diffutil

import androidx.recyclerview.widget.DiffUtil
import com.kamathtanay.kblock.model.ContactItem

class ContactDiffUtil : DiffUtil.ItemCallback<ContactItem>() {
    override fun areItemsTheSame(oldItem: ContactItem, newItem: ContactItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: ContactItem, newItem: ContactItem): Boolean {
        return oldItem.contactNumber.equals(newItem.contactNumber) &&
                oldItem.contactName.equals(newItem.contactName) &&
                oldItem.iconId == newItem.iconId
    }
}
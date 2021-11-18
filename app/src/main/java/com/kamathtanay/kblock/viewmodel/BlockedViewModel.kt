package com.kamathtanay.kblock.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.kamathtanay.kblock.R
import com.kamathtanay.kblock.data.db.entity.Contact
import com.kamathtanay.kblock.data.repository.BlockedRepository
import com.kamathtanay.kblock.model.ContactItem
import java.util.*

class BlockedViewModel(private val repository: BlockedRepository):ViewModel() {
    private var allBlockedContacts: LiveData<List<Contact>>

    init {
        allBlockedContacts=repository.getAllBlockedContacts()
    }

    fun prepareDataForBlockedRecyclerView(contactsList: List<Contact>): MutableList<ContactItem> {
        return contactsList.map { contact ->
            ContactItem(
                contact.id, contact.contactName, contact.contactPhoneNumber,
                if (contact.isBlocked) R.drawable.ic_baseline_block_24 else R.drawable.ic_baseline_unblock_24
            )
        }.toMutableList()
    }

    fun getAllBlockedContacts(): LiveData<List<Contact>> {
        return allBlockedContacts
    }

    fun unblockContact(isBlocked: Boolean = false, contactPhoneNo: String) {
        repository.unblockContact(isBlocked, contactPhoneNo)
    }

    fun filterBlockedList(query: String): MutableList<ContactItem> {
        val filteredBlockedList = mutableListOf<ContactItem>()
        val originalBlockedList = prepareDataForBlockedRecyclerView(allBlockedContacts.value!!)

        for (row in originalBlockedList) {
            if (row.contactName.lowercase(Locale.getDefault()).contains(query.lowercase(Locale.getDefault()))
                || row.contactNumber.contains(query)) {
                filteredBlockedList.add(row)
            }
        }
        return filteredBlockedList
    }
}
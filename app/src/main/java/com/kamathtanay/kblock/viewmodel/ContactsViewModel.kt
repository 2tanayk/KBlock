package com.kamathtanay.kblock.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kamathtanay.kblock.R
import com.kamathtanay.kblock.data.db.entity.Contact
import com.kamathtanay.kblock.data.repository.ContactsRepository
import com.kamathtanay.kblock.model.ContactItem
import kotlinx.coroutines.launch
import java.util.*

class ContactsViewModel(private val repository: ContactsRepository) : ViewModel() {
    private var allUserContacts: LiveData<List<Contact>>

    init {
        allUserContacts = repository.getAllUserContacts()
    }

    fun importAllContacts() {
        viewModelScope.launch {
            Log.e("Contact fetch", "Started...")
            repository.importAllContacts()
            Log.e("Contact fetch", "ended and posted...")
        }
    }

    fun getAllUserContacts(): LiveData<List<Contact>> {
        return allUserContacts
    }

    fun updateOnContactBlockedUnblocked(isBlocked: Boolean, contactPhoneNo: String){
        repository.updateOnContactBlockUnblock(isBlocked,contactPhoneNo)
    }

    fun prepareDataForContactsRecyclerView(contactsList: List<Contact>): MutableList<ContactItem> {
        return contactsList.map { contact ->
            ContactItem(
                contact.id, contact.contactName, contact.contactPhoneNumber,
                if (contact.isBlocked) R.drawable.ic_baseline_block_24 else R.drawable.ic_baseline_unblock_24
            )
        }.toMutableList()
    }

    fun filterContactsList(query: String): MutableList<ContactItem> {
        val filteredContactList = mutableListOf<ContactItem>()
        val originalContactList = prepareDataForContactsRecyclerView(allUserContacts.value!!)

        for (row in originalContactList) {
            if (row.contactName.lowercase(Locale.getDefault()).contains(query.lowercase(Locale.getDefault()))
                || row.contactNumber.contains(query)) {
                filteredContactList.add(row)
            }
        }
        return filteredContactList
    }
}
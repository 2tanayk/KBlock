package com.kamathtanay.kblock.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kamathtanay.kblock.data.db.entity.Contact
import com.kamathtanay.kblock.data.repository.ContactsRepository
import kotlinx.coroutines.launch

class ContactsViewModel(private val repository: ContactsRepository) : ViewModel() {

    private val allContacts = MutableLiveData<List<Contact>>()
    private lateinit var allUserContacts: LiveData<List<Contact>>

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
}
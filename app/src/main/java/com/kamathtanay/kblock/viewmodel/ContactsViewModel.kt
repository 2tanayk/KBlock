package com.kamathtanay.kblock.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kamathtanay.kblock.data.db.entity.Contact
import com.kamathtanay.kblock.data.repository.ContactsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ContactsViewModel(private val repository: ContactsRepository) : ViewModel() {

    val allContacts = MutableLiveData<List<Contact>>()

    fun getAllUserContacts(): LiveData<List<Contact>> {
        viewModelScope.launch {
            Log.e("Contact fetch","Started...")
            val contacts = repository.getAllContacts()
            Log.e("Contact fetch","value ${contacts.value}")
            allContacts.postValue(contacts.value)
            Log.e("Contact fetch","ended and posting...")
        }
        Log.e("Contact fetch","returning....")
        return allContacts
    }
}
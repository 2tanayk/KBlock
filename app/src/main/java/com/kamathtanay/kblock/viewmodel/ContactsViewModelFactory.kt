package com.kamathtanay.kblock.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kamathtanay.kblock.data.repository.ContactsRepository

@Suppress("UNCHECKED_CAST")
class ContactsViewModelFactory(
    private val repository: ContactsRepository
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ContactsViewModel(repository) as T
    }
}
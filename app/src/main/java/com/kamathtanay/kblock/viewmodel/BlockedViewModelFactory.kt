package com.kamathtanay.kblock.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kamathtanay.kblock.data.repository.BlockedRepository
import com.kamathtanay.kblock.data.repository.ContactsRepository

@Suppress("UNCHECKED_CAST")
class BlockedViewModelFactory(
    private val repository: BlockedRepository
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return BlockedViewModel(repository) as T
    }
}
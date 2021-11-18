package com.kamathtanay.kblock.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kamathtanay.kblock.data.repository.CallLogRepository

@Suppress("UNCHECKED_CAST")
class CallLogViewModelFactory(private val repository: CallLogRepository) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return CallLogViewModel(repository) as T
    }
}
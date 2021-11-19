package com.kamathtanay.kblock.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.kamathtanay.kblock.data.db.entity.CallLog
import com.kamathtanay.kblock.data.repository.CallLogRepository
import com.kamathtanay.kblock.model.CallLogItem
import java.util.*

class CallLogViewModel(private val repository: CallLogRepository) : ViewModel() {
    private var allCallLogs: LiveData<List<CallLog>>

    init {
        allCallLogs=repository.getAllCallLogs()
    }

    fun getAllCallLogs(): LiveData<List<CallLog>> {
        return allCallLogs
    }

    fun prepareDataForCallLogsRecyclerView(logsList: List<CallLog>): MutableList<CallLogItem> {
        return logsList.map { callLog ->
            CallLogItem(callLog.id, callLog.logName, callLog.logPhoneNumber, callLog.logTime)
        }.toMutableList()
    }

    fun filterCallLogsList(query: String): MutableList<CallLogItem> {
        val filteredCallLogsList = mutableListOf<CallLogItem>()
        val originalCallLogsList = prepareDataForCallLogsRecyclerView(allCallLogs.value!!)

        for (row in originalCallLogsList) {
            if (row.contactName.lowercase(Locale.getDefault()).contains(query.lowercase(Locale.getDefault()))
                || row.contactNumber.contains(query)) {
                filteredCallLogsList.add(row)
            }
        }
        return filteredCallLogsList
    }

    fun deleteAllCallLogs() {
        repository.deleteAllCallLogs()
    }
}
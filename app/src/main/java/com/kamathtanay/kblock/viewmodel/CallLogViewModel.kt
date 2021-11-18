package com.kamathtanay.kblock.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.kamathtanay.kblock.data.db.entity.CallLog
import com.kamathtanay.kblock.data.repository.CallLogRepository
import com.kamathtanay.kblock.model.CallLogItem

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

}
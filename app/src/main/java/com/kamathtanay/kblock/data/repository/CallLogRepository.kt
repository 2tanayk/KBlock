package com.kamathtanay.kblock.data.repository

import androidx.lifecycle.LiveData
import com.kamathtanay.kblock.data.dao.CallLogDao
import com.kamathtanay.kblock.data.db.AppDatabase
import com.kamathtanay.kblock.data.db.entity.CallLog
import com.kamathtanay.kblock.util.CoroutineUtil

class CallLogRepository(private val db: AppDatabase) {
    private var callLogDao: CallLogDao
    private var allCallLogs: LiveData<List<CallLog>>

    init {
        callLogDao = db.getCallLogDao()
        allCallLogs = callLogDao.getAllCallLogs()
    }

    fun getAllCallLogs(): LiveData<List<CallLog>> {
        return allCallLogs
    }

    fun insertNewCallLog(callLog: CallLog) {
        CoroutineUtil.io {
            callLogDao.insertNewCallLog(callLog)
        }
    }

    fun deleteAllCallLogs() {
        CoroutineUtil.io {
            callLogDao.deleteAllCallLogs()
        }
    }
}
package com.kamathtanay.kblock.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.kamathtanay.kblock.data.db.entity.CallLog

@Dao
interface CallLogDao {
    @Query("SELECT * FROM call_log_table ORDER BY log_id DESC")
    fun getAllCallLogs(): LiveData<List<CallLog>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNewCallLog(callLog: CallLog)
}
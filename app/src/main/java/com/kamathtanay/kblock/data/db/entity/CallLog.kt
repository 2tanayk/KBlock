package com.kamathtanay.kblock.data.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "call_log_table")
data class CallLog(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "log_id")
    val id:Int=0,
    @ColumnInfo(name = "log_name")
    val logName: String,
    @ColumnInfo(name = "log_phone_number")
    val logPhoneNumber: String,
    @ColumnInfo(name = "log_time")
    val logTime:String,
)
